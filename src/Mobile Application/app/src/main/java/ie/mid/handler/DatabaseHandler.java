package ie.mid.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ie.mid.R;
import ie.mid.enums.CardStatus;
import ie.mid.enums.DataType;
import ie.mid.model.AvailableCard;
import ie.mid.model.CardField;
import ie.mid.model.CardType;
import ie.mid.model.CreatedSubmission;
import ie.mid.model.Field;
import ie.mid.model.Profile;

public class DatabaseHandler {

    //Database variables
    private static final String DATA_BASE_NAME = "mid_database";
    private static final int DATABASE_VERSION = 1;
    private static final String ID = "id";
    private static final String CREATION_DATE = "creation_date";
    private static final String UPDATED_DATE = "updated_date";

    //Profile data variables
    private static final String PROFILE_NAME = "profile_name";
    private static final String PROFILE_IMG = "profile_img";
    private static final String SALT = "salt";
    private static final String HASH = "hash";
    private static final String SERVER_ID = "server_id";
    private static final String USER_TOKEN = "user_token";
    private static final String PRIVATE_KEY = "private_key";
    private static final String PUBLIC_KEY = "public_key";
    private static final String PROFILE_TABLE_NAME = "profiles";
    private static final String PROFILE_TABLE_CREATE = "create table " + PROFILE_TABLE_NAME + " (" +
            ID + " text not null, " +
            PROFILE_NAME + " text not null," +
            PROFILE_IMG + " text not null," +
            SALT + " text not null," +
            HASH + " text not null," +
            SERVER_ID + " text not null," +
            USER_TOKEN + " text not null," +
            PRIVATE_KEY + " text not null," +
            PUBLIC_KEY + " text not null," +
            CREATION_DATE + " text not null," +
            UPDATED_DATE + " text not null" +
            ");";

    //Card data variables
    private static final String CARD_ID = "card_id";
    private static final String PARTY_ID = "party_id";
    private static final String TITLE = "title";
    private static final String COVER_IMG_URL = "cover_img_url";
    private static final String FIELD_NAME = "field_name";
    private static final String FIELD_VALUE = "field_value";
    private static final String OWNER_ID = "owner_id";
    private static final String SUBMISSION_ID = "submission_id";
    private static final String VERSION_NUMBER = "version_number";
    private static final String STATUS_CODE = "status_code";
    private static final String CARD_TABLE_NAME = "cards";
    private static final String CARD_TABLE_CREATE = "create table " + CARD_TABLE_NAME + " (" +
            ID + " text not null, " +
            TITLE + " text not null," +
            CARD_ID + " text not null," +
            PARTY_ID + " text not null," +
            OWNER_ID + " text not null," +
            SUBMISSION_ID + " text," +
            FIELD_NAME + " text not null," +
            FIELD_VALUE + " text not null," +
            COVER_IMG_URL + " text not null," +
            VERSION_NUMBER + " integer not null," +
            STATUS_CODE + " text not null," +
            CREATION_DATE + " text not null," +
            UPDATED_DATE + " text not null" +
            ");";

    //Available card variables
    private static final String DESCRIPTION = "description";
    private static final String CARD_FIELDS = "card_fields";
    private static final String ICON_IMG_URL = "icon_img_url";
    private static final String BACKEND_ID = "backend_id";
    private static final String AVAILABLE_CARD_TABLE_NAME = "available_cards";
    private static final String AVAILABLE_CARD_TABLE_CREATE = "create table " + AVAILABLE_CARD_TABLE_NAME + " (" +
            ID + " text not null, " +
            TITLE + " text not null, " +
            DESCRIPTION + " text not null," +
            CARD_FIELDS + " text not null," +
            BACKEND_ID + " text not null, " +
            COVER_IMG_URL + " text not null," +
            ICON_IMG_URL + " text not null," +
            VERSION_NUMBER + " text not null" +
            ");";

    //FCM database
    private static final String FCM_TOKEN = "fcm_token";
    private static final String FCM_TABLE_NAME = "fcm_token";
    private static final String FCM_TABLE_NAME_CREATE = "create table " + FCM_TABLE_NAME + " (" +
            ID + " text not null, " +
            FCM_TOKEN + " text not null, " +
            CREATION_DATE + " text not null," +
            UPDATED_DATE + " text not null" +
            ");";

    //Submission database
    private static final String SUBMISSION_KEY = "submission_key";
    private static final String SUBMISSION_DATA = "submission_data";
    private static final String SUBMISSION_TABLE_NAME = "submission_table";
    private static final String SUBMISSION_TABLE_NAME_CREATE = "create table " + SUBMISSION_TABLE_NAME + " (" +
            ID + " text not null, " +
            SUBMISSION_ID + " text not null, " +
            SUBMISSION_KEY + " text not null," +
            SUBMISSION_DATA + " text not null," +
            CARD_ID + " text not null," +
            CREATION_DATE + " text not null," +
            UPDATED_DATE + " text not null" +
            ");";


    private static final String[] TABLE_NAMES = new String []{PROFILE_TABLE_NAME,CARD_TABLE_NAME,AVAILABLE_CARD_TABLE_NAME,FCM_TABLE_NAME,SUBMISSION_TABLE_NAME};
    private static final String[] CREATION_SCRIPTS = new String []{PROFILE_TABLE_CREATE,CARD_TABLE_CREATE,AVAILABLE_CARD_TABLE_CREATE,FCM_TABLE_NAME_CREATE,SUBMISSION_TABLE_NAME_CREATE};
    private DataBaseHelper dbhelper;
    private SQLiteDatabase db;


    public DatabaseHandler(Context ctx)
    {
        dbhelper = new DataBaseHelper(ctx);
    }



    private static class DataBaseHelper extends SQLiteOpenHelper {

        DataBaseHelper(Context ctx)
        {
            super(ctx,DATA_BASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try
            {
                for(String script: CREATION_SCRIPTS){
                    db.execSQL(script);
                }
            }

            catch(SQLException e)
            {
                Log.e("Error", e.getMessage());
            }
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            for(String name: TABLE_NAMES){
                db.execSQL("DROP TABLE IF EXISTS " + name);
            }
            onCreate(db);
        }
    }

    public DatabaseHandler open() {
        db = dbhelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbhelper.close();
    }

    private String getTimestamp(){
        return new Timestamp(new Date().getTime()).toString();
    }

    public Profile createProfile(String name,String profileImg,String hash,String salt,String serverId,String userToken, String publicKey,String privateKey){
        String id = UUID.randomUUID().toString();
        String date = new Date().toString();
        ContentValues content = new ContentValues();
        content.put(ID, id);
        content.put(PROFILE_NAME,name);
        content.put(PROFILE_IMG,profileImg);
        content.put(SERVER_ID,serverId);
        content.put(HASH, hash);
        content.put(SALT, salt);
        content.put(USER_TOKEN, userToken);
        content.put(PRIVATE_KEY,privateKey);
        content.put(PUBLIC_KEY,publicKey);
        content.put(CREATION_DATE, date);
        content.put(UPDATED_DATE, date);
        db.insert(PROFILE_TABLE_NAME,null,content);
        return new Profile(id,name,profileImg,hash,salt,serverId,userToken,publicKey,privateKey);
    }

    public CreatedSubmission createSubmission(String submissionId, String cardId, String submissionData,String submissionKey){
        String id = UUID.randomUUID().toString();
        ContentValues content = new ContentValues();
        content.put(ID, id);
        content.put(SUBMISSION_ID,submissionId);
        content.put(SUBMISSION_DATA, submissionData);
        content.put(SUBMISSION_KEY, submissionKey);
        content.put(CARD_ID,cardId);
        content.put(CREATION_DATE, getTimestamp());
        content.put(UPDATED_DATE, getTimestamp());
        db.insert(SUBMISSION_TABLE_NAME,null,content);
        return new CreatedSubmission(id,submissionId,cardId,CardStatus.PENDING.toString());
    }

    public String createCard(String partyId, String cardId, String title, String owner, String fieldName, String fieldValue, String coverImg, String statusCode, int versionNumber) {
        String id = UUID.randomUUID().toString();
        String date = new Date().toString();
        ContentValues content = new ContentValues();
        content.put(ID, id);
        content.put(CARD_ID, cardId);
        content.put(PARTY_ID, partyId);
        content.put(TITLE,title);
        content.put(OWNER_ID,owner);
        content.put(COVER_IMG_URL, coverImg);
        content.put(FIELD_NAME, fieldName);
        content.put(FIELD_VALUE, fieldValue);
        content.put(STATUS_CODE,statusCode);
        content.put(CREATION_DATE, date);
        content.put(UPDATED_DATE, date);
        content.put(VERSION_NUMBER,versionNumber);
        db.insert(CARD_TABLE_NAME,null,content);
        return id;
    }

    public String createFcm(String fcm){
        String id = UUID.randomUUID().toString();
        String date = new Date().toString();
        ContentValues content = new ContentValues();
        content.put(ID, id);
        content.put(FCM_TOKEN,fcm);
        content.put(CREATION_DATE, date);
        content.put(UPDATED_DATE, date);
        db.insert(FCM_TABLE_NAME,null,content);
        return id;
    }

    public int returnFcmCount()
    {
        return (int) DatabaseUtils.queryNumEntries(db, FCM_TABLE_NAME);
    }

    public int returnSubmissionCount() {
        return (int) DatabaseUtils.queryNumEntries(db, SUBMISSION_TABLE_NAME);
    }

    public int returnAmountOfProfiles() {
        return (int) DatabaseUtils.queryNumEntries(db, PROFILE_TABLE_NAME);
    }

    public CreatedSubmission getSubmission(String id) {
        String selectQuery = "SELECT ID, SUBMISSION_ID,CARD_ID,SUBMISSION_DATA,SUBMISSION_KEY,CREATION_DATE,UPDATED_DATE FROM " + SUBMISSION_TABLE_NAME + " WHERE " + SUBMISSION_ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        CreatedSubmission createdSubmission = new CreatedSubmission();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                createdSubmission = new CreatedSubmission(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
            }
            return createdSubmission;
        }
        return null;
    }

    public Profile getProfile(String id) {
        String selectQuery = "SELECT ID, PROFILE_NAME,PROFILE_IMG,HASH,SALT,SERVER_ID,USER_TOKEN,PUBLIC_KEY,PRIVATE_KEY FROM " + PROFILE_TABLE_NAME + " WHERE " + ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Profile profile = new Profile();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                profile = new Profile(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                );
            }
            return profile;
        }
        return null;
    }

    public Profile getProfileByServerId(String id) {
        String selectQuery = "SELECT ID, PROFILE_NAME,PROFILE_IMG,HASH,SALT,SERVER_ID,USER_TOKEN,PUBLIC_KEY,PRIVATE_KEY FROM " + PROFILE_TABLE_NAME + " WHERE " + SERVER_ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Profile profile = new Profile();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                profile = new Profile(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                );
            }
            return profile;
        }
        return null;
    }

    public List<CardType> getUserCards(String id) {
        String selectQuery = "SELECT ID ,TITLE ,CARD_ID ,OWNER_ID ,FIELD_NAME ,FIELD_VALUE, COVER_IMG_URL,STATUS_CODE,PARTY_ID,SUBMISSION_ID,VERSION_NUMBER FROM " + CARD_TABLE_NAME + " WHERE " + OWNER_ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<CardType> cardDataList = new ArrayList<>();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                CardType cardType = new CardType();
                cardType.setId(cursor.getString(0));
                cardType.setTitle(cursor.getString(1));
                cardType.setCardId(cursor.getString(2));
                cardType.setOwnerId(cursor.getString(3));
                cardType.setDefaultColor(R.color.colorPrimary);
                cardType.setStatus(cursor.getString(7));
                cardType.setImageUrl(cursor.getString(6));
                cardType.setPartyId(cursor.getString(8));
                cardType.setSubmissionId(cursor.getString(9));
                cardType.setVersionNumber(cursor.getInt(10));
                List<Field> fieldList = getFieldList(cursor.getString(4));
                cardType.setDataList(getCardData(fieldList, cursor.getString(5)));
                cardDataList.add(cardType);
            }
            return cardDataList;
        }
        return null;
    }

    public CardType getUserCardBySubmission(String submissionId) {
        String selectQuery = "SELECT ID ,TITLE ,CARD_ID ,OWNER_ID ,FIELD_NAME ,FIELD_VALUE, COVER_IMG_URL,STATUS_CODE,PARTY_ID,SUBMISSION_ID,VERSION_NUMBER FROM " + CARD_TABLE_NAME + " WHERE " + SUBMISSION_ID + "='" + submissionId + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        CardType cardType = new CardType();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                cardType.setId(cursor.getString(0));
                cardType.setTitle(cursor.getString(1));
                cardType.setCardId(cursor.getString(2));
                cardType.setOwnerId(cursor.getString(3));
                cardType.setDefaultColor(R.color.colorPrimary);
                cardType.setStatus(cursor.getString(7));
                cardType.setImageUrl(cursor.getString(6));
                cardType.setPartyId(cursor.getString(8));
                cardType.setSubmissionId(cursor.getString(9));
                cardType.setVersionNumber(cursor.getInt(10));
                List<Field> fieldList = getFieldList(cursor.getString(4));
                cardType.setDataList(getCardData(fieldList, cursor.getString(5)));
            }
            return cardType;
        }
        return null;
    }

    public CardType getUserCardByIdentityType(String userId,String identityId) {
        String selectQuery = "SELECT ID ,TITLE ,CARD_ID ,OWNER_ID ,FIELD_NAME ,FIELD_VALUE, COVER_IMG_URL,STATUS_CODE,PARTY_ID,SUBMISSION_ID,VERSION_NUMBER FROM " + CARD_TABLE_NAME + " WHERE " + CARD_ID + "='" + identityId +"' AND " +OWNER_ID + " ='"+userId+ "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        CardType cardType = new CardType();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                cardType.setId(cursor.getString(0));
                cardType.setTitle(cursor.getString(1));
                cardType.setCardId(cursor.getString(2));
                cardType.setOwnerId(cursor.getString(3));
                cardType.setDefaultColor(R.color.colorPrimary);
                cardType.setStatus(cursor.getString(7));
                cardType.setImageUrl(cursor.getString(6));
                cardType.setPartyId(cursor.getString(8));
                cardType.setSubmissionId(cursor.getString(9));
                cardType.setVersionNumber(cursor.getInt(10));
                List<Field> fieldList = getFieldList(cursor.getString(4));
                cardType.setDataList(getCardData(fieldList, cursor.getString(5)));
            }
            return cardType;
        }
        return null;
    }

    public CardType getUserCard(String id) {
        String selectQuery = "SELECT ID ,TITLE ,CARD_ID ,OWNER_ID ,FIELD_NAME ,FIELD_VALUE, COVER_IMG_URL,STATUS_CODE,PARTY_ID,SUBMISSION_ID,VERSION_NUMBER FROM " + CARD_TABLE_NAME + " WHERE " + ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        CardType cardType = new CardType();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                cardType.setId(cursor.getString(0));
                cardType.setTitle(cursor.getString(1));
                cardType.setCardId(cursor.getString(2));
                cardType.setOwnerId(cursor.getString(3));
                cardType.setDefaultColor(R.color.colorPrimary);
                cardType.setStatus(cursor.getString(7));
                cardType.setImageUrl(cursor.getString(6));
                cardType.setPartyId(cursor.getString(8));
                cardType.setSubmissionId(cursor.getString(9));
                cardType.setVersionNumber(cursor.getInt(10));
                List<Field> fieldList = getFieldList(cursor.getString(4));
                cardType.setDataList(getCardData(fieldList, cursor.getString(5)));
            }
            return cardType;
        }
        return null;
    }

    private List<Field> getFieldList(String fieldString) {
        String[] fieldArray = fieldString.split(",");
        List<Field> fieldList = new ArrayList<>();
        for (String field : fieldArray) {
            DataType fieldType = DataType.findDataType(field.substring(field.indexOf(":") + 1));
            if (fieldType != null) {
                fieldList.add(new Field(field.substring(0, field.indexOf(":")), fieldType.toString()));
            }
        }
        return fieldList;
    }

    private ArrayList<CardField> getCardData(List<Field> fieldList, String values) {
        ArrayList<CardField> cardFieldList = new ArrayList<>();
        String[] valuesArray = values.split(",");
        for (int i = 0; i < valuesArray.length; i++) {
            CardField cardField = new CardField(valuesArray[i], fieldList.get(i).getType());
            cardField.setDataIcon(getDataIcon(fieldList.get(i).getType()));
            cardField.setFieldTitle(fieldList.get(i).getName());
            cardFieldList.add(cardField);
        }
        return cardFieldList;
    }

    private int getDataIcon(String type) {
        switch (type) {
            case "KEY":
                return R.drawable.ic_vpn_key_black_48dp;
            case "EXPIRY":
                return R.drawable.ic_date_range_black_48dp;
            case "FIRSTNAME":
                return R.drawable.ic_person_black_48dp;
            case "SURNAME":
                return R.drawable.ic_person_black_48dp;
            case "ADDRESS":
                return R.drawable.ic_home_black_48dp;
            case "BIRTHDAY":
                return R.drawable.ic_cake_black_48dp;
            default:
                return R.drawable.ic_person_black_48dp;
        }
    }

    public List<Profile> returnProfiles() {
        Cursor cursor =  db.query(PROFILE_TABLE_NAME, new String[]{ID, PROFILE_NAME,PROFILE_IMG,HASH,SALT,SERVER_ID,USER_TOKEN,PRIVATE_KEY,PUBLIC_KEY}, null, null, null, null, null);
        List<Profile> profiles = new ArrayList<>();
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                profiles.add(new Profile(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                ));
            }
        }
        return profiles;
    }

    public String returnFcmToken(){
        Cursor cursor =  db.query(FCM_TABLE_NAME, new String[]{FCM_TOKEN}, null, null, null, null, null);
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                return cursor.getString(0);
            }
        }
        return null;
    }

    private String getFcmTokenId(){
        Cursor cursor =  db.query(FCM_TABLE_NAME, new String[]{ID}, null, null, null, null, null);
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                return cursor.getString(0);
            }
        }
        return null;
    }

    public boolean updateFcm(String fcm) {
        String id = getFcmTokenId();
        ContentValues content = new ContentValues();
        content.put(FCM_TOKEN,fcm);
        db.update(FCM_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public boolean hasCards(String id) {
        String selectQuery = "SELECT ID FROM " + CARD_TABLE_NAME + " WHERE " + OWNER_ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount() != 0;
    }

    public boolean updateCardStatus(String id, String status) {
        ContentValues content = new ContentValues();
        content.put(STATUS_CODE, status);
        db.update(CARD_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public boolean updateSubmissionId(String id, String submissionId) {
        ContentValues content = new ContentValues();
        content.put(SUBMISSION_ID, submissionId);
        db.update(CARD_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public boolean updateCard(String id,String fields) {
        ContentValues content = new ContentValues();
        content.put(FIELD_VALUE,fields);
        db.update(CARD_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public boolean updateProfileName(String id,String name) {
        ContentValues content = new ContentValues();
        content.put(PROFILE_NAME,name);
        db.update(PROFILE_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public boolean updateProfileImg(String id,String path) {
        ContentValues content = new ContentValues();
        content.put(PROFILE_IMG,path);
        db.update(PROFILE_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public boolean updateProfileSecurity(String id,String salt, String hash) {
        ContentValues content = new ContentValues();
        content.put(SALT,salt);
        content.put(HASH,hash);
        db.update(PROFILE_TABLE_NAME,content,ID + " = ?", new String[] { id });
        return true;
    }

    public void removeCard(String id)
    {
        db.delete(CARD_TABLE_NAME, ID + " = ?", new String[]{id});
    }

    public void removeProfile(String id) {
        db.delete(PROFILE_TABLE_NAME, ID + " = ?", new String[]{id});
    }

    public void removeAvailableCard(String id) {
        db.delete(AVAILABLE_CARD_TABLE_NAME, ID + " = ?", new String[]{id});
    }

    public void purgeDatabase(){
        for(String table: TABLE_NAMES){
            if(!table.equals(FCM_TABLE_NAME)) //keep track of fcm for future use
                db.delete(table, null, null);
        }
    }
}
