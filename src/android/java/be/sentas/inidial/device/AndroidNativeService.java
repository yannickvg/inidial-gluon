package be.sentas.inidial.device;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import be.sentas.inidial.Utils;
import be.sentas.inidial.model.Contact;
import be.sentas.inidial.model.Phone;
import be.sentas.inidial.model.PhoneType;
import javafxports.android.FXActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class AndroidNativeService implements NativeService {

    private static final String TAG = "InidialGluon";

    @Override
    public void callNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        FXActivity.getInstance().startActivity(intent);
    }

    @Override
    public void sendTextMessage(String number) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + number));
        FXActivity.getInstance().startActivity(intent);
    }

    @Override
    public List<Contact> getContacts() {
        ContentResolver cr = FXActivity.getInstance().getContentResolver();
        List<Contact> contacts = new ArrayList<Contact>();
        String id;
        String[] projection = { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER };
        String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + "='1'";
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, projection,
                selection, null, null);
        cur.moveToFirst();
        if (cur.getCount() > 0) {
            do {
                Contact c = new Contact();
                id = cur.getString(0);
                c.setId(id);
                fillNameDetails(id, c, cur.getString(1), cr);
                setImageAvailability(cr, c);
                if (Integer
                        .parseInt(cur.getString(2)) > 0 && c.hasName()) {
                    contacts.add(c);
                }
            } while (cur.moveToNext());
        }

        Hashtable<String, List<Phone>> contactPhoneInfo = new Hashtable<String, List<Phone>>();
        for (Contact contact : contacts) {
            contactPhoneInfo.put(contact.getId(), new ArrayList<Phone>());
        }

        //add phones
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String [] PHONES_PROJECTION = new String[] {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE};
        Cursor phones = cr.query(phoneUri,
                PHONES_PROJECTION,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY + " DESC");

        if (phones.getCount() > 0) {
            phones.moveToFirst();
            Set keySet = contactPhoneInfo.keySet();
            int idColumnIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
            int numColumnIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int typeColumnIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
            do {
                String contactId = phones.getString(idColumnIndex);
                if(keySet.contains(contactId)){
                    contactPhoneInfo.get(contactId).add(new Phone(phones.getString(numColumnIndex), getType(phones.getString(typeColumnIndex))));
                }
            } while(phones.moveToNext());
        }

        //update phone objects
        for (Contact contact : contacts) {
            List<Phone> list = contactPhoneInfo.get(contact.getId());
            int pid = 1;
            for (Phone phone : list) {
                phone.setContactId(contact.getId());
                phone.setId(contact.getId() + pid);
                pid++;
                contact.getNumbers().add(phone);
            }
        }
        return (contacts);
    }

    private void setImageAvailability(ContentResolver cr, Contact c) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
                Long.valueOf(c.getId()));
        InputStream stream = ContactsContract.Contacts.openContactPhotoInputStream(cr, contactUri, false);
        if (stream != null) {
            c.setHasImageData(true);
        } else {
            c.setHasImageData(false);
        }
    }

    private PhoneType getType(String type) {
        if (type.equals("1")) {
            return PhoneType.HOME;
        } else if (type.equals("2")) {
            return PhoneType.MOBILE;
        } else if (type.equals("3")) {
            return PhoneType.WORK;
        } else {
            return PhoneType.OTHER;
        }
    }

    private void fillNameDetails(String id, Contact c, String displayName, ContentResolver cr) {
        String whereName = ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereNameParams = new String[] { id , ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE };
        Cursor nameCur = cr.query(ContactsContract.Data.CONTENT_URI, null, whereName, whereNameParams, null);
        while (nameCur.moveToNext()) {
            String given = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
            String family = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
            String middle = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME));
            c.setFirstName(Utils.isNotBlank(given) ? given : "");
            c.setLastName((Utils.isNotBlank(middle) ? middle + " " : "") + (Utils.isNotBlank(family) ? family : ""));
        }
        if(Utils.isBlank(c.getFirstName()) && Utils.isBlank(c.getLastName())) {
            c.setLastName(displayName);
        }
        nameCur.close();
    }

    public byte[] getContactPicture(String contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
                Long.valueOf(contactId));
        return convertImageToByte(contactUri);
    }

    public byte[] convertImageToByte(Uri uri){
        InputStream inputStream = ContactsContract.Contacts
                .openContactPhotoInputStream(FXActivity.getInstance().getContentResolver(), uri, false);
        if (inputStream != null) {
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return baos.toByteArray();
        }
        return null;
    }

    public List<Contact> getDummyContacts() {
        List<Contact> persons = new ArrayList<Contact>();
        Contact p1 = new Contact("John", "Jacobs");
        p1.getNumbers().add(new Phone("+3234561234", PhoneType.HOME));
        p1.getNumbers().add(new Phone("+3249755343", PhoneType.WORK));
        p1.getNumbers().add(new Phone("+3234561234", PhoneType.MOBILE));
        persons.add(p1);
        Contact p2 = new Contact("Peter","Van der Beek");
        p2.getNumbers().add(new Phone("+3234777777", PhoneType.HOME));
        p2.getNumbers().add(new Phone("+3234561234", PhoneType.MOBILE));
        persons.add(p2);
        Contact p3 = new Contact("Tim", "Heffner");
        p3.getNumbers().add(new Phone("+32497123456", PhoneType.HOME));
        p3.getNumbers().add(new Phone("+3234561234", PhoneType.OTHER));
        p3.getNumbers().add(new Phone("+32326547894", PhoneType.MOBILE));
        persons.add(p3);
        Contact p4 = new Contact("Andrew","Totter");
        p4.getNumbers().add(new Phone("+3234561234", PhoneType.HOME));
        persons.add(p4);
        Contact p5 = new Contact("Yannick", "Epson");
        p5.getNumbers().add(new Phone("+3234561234", PhoneType.HOME));
        p5.getNumbers().add(new Phone("+32497123456", PhoneType.WORK));
        persons.add(p5);
        Contact p6 = new Contact("Herman", "Baker");
        p6.getNumbers().add(new Phone("+3234561234", PhoneType.HOME));
        p6.getNumbers().add(new Phone("+32497123456", PhoneType.WORK));
        persons.add(p6);
        Contact p7 = new Contact("Marc", "Omar");
        p7.getNumbers().add(new Phone("+3234561234", PhoneType.HOME));
        p7.getNumbers().add(new Phone("+32497123456", PhoneType.WORK));
        persons.add(p7);
        Contact p8 = new Contact("James", "Wagner");
        p8.getNumbers().add(new Phone("+3234561234", PhoneType.HOME));
        p8.getNumbers().add(new Phone("+3249755343", PhoneType.OTHER));
        p8.getNumbers().add(new Phone("+3234561234", PhoneType.MOBILE));
        persons.add(p8);
        return persons;
    }
}
