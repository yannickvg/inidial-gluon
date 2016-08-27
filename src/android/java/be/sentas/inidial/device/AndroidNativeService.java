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

/**
 * Created by yannick on 15/08/16.
 */
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
                .openContactPhotoInputStream(FXActivity.getInstance().getContentResolver(), uri, true);
        if (inputStream != null) {
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return baos.toByteArray();
        }
        return null;
    }
}
