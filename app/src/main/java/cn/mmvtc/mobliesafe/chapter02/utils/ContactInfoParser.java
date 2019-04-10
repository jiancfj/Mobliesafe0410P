package cn.mmvtc.mobliesafe.chapter02.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cn.mmvtc.mobliesafe.chapter02.entity.ContactInfo;

/**
 * Created by Administrator on 2019/4/10.
 * 功能：联系人信息存储在SQLite数据库，
 * 获取联系人的id，根据id在data表中查询联系人的名字和电话号码，
 * 并封装到ContactInfo类，然后存入到List集合。
 */

public class ContactInfoParser {
    public static List<ContactInfo> getSystemContact(Context context) {
        //步骤1：内容访问者获取
        ContentResolver resolver = context.getContentResolver();  //内容访问者。
        //步骤2：获取内容访问者的uri数据。（raw_contacts、data表）
        Uri uri = Uri.parse("content://com.android.contact/raw_contacts");
        Uri datauri = Uri.parse("content://com.android.contat/data");
        //步骤3：获取联系人表raw_contacts中的id，根据id获取data表的姓名和电话号码。
        List<ContactInfo> infos = new ArrayList<ContactInfo>();//存放信息
        //查询raw_contacts的contact_id。
        Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
        //用while遍历cursor
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            if (id != null) {
                System.out.println("联系人id：" + id);
                ContactInfo info = new ContactInfo();
                info.id = id;
                //根据id查询data表的姓名、电话号码。
                Cursor datacursor = resolver.query(datauri, new String[]{
                                "data1", "mimetype"}, "raw_contact_id=?",
                        new String[]{id}, null);
                while (datacursor.moveToNext()) {
                    String data1 = datacursor.getString(0);
                    String mimetype = datacursor.getString(1);
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        System.out.println("姓名：" + data1);
                        info.name=data1;
                    } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                        System.out.println("电话：" + data1);
                        info.phone=data1;
                    }
                }
                infos.add(info);
                datacursor.close();
            }
        }
        cursor.close();
        return infos;
    }
}
