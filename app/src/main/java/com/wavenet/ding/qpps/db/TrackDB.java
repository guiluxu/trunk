package com.wavenet.ding.qpps.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.wavenet.ding.qpps.db.bean.TaskEvenBean;
import com.wavenet.ding.qpps.db.bean.TaskInfoBean;
import com.wavenet.ding.qpps.db.bean.TrackBean;
import com.wavenet.ding.qpps.db.bean.WalkBean;
import com.wavenet.ding.qpps.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 所有用户
 * Created by Admin on 2016/6/6.
 */
public class TrackDB {
    private static DBHelper helper;
    private static SQLiteDatabase db;

//   public Context mContext;
//    DatabaseContext dbContext;
    DatabaseContext mContext;
    public TrackDB(Context context) {
//        this.mContext = context;
        mContext = new DatabaseContext(context);
        helper=DBHelper.getInstance(mContext);///单例模式
    }
    /**
     *  添加巡检任务记录
     *
     * @param bean
     */
    public  synchronized void inserttaskinfo( TaskInfoBean bean) {
        try{
            db=helper.getReadableDatabase();
            db.beginTransaction();
            //生成ContentValues对象 //key:列名，value:想插入的值
             //往ContentValues对象存放数据，键-值对模式
            ContentValues cv = ReflectUtil.getFields(bean);
           //调用insert方法，将数据插入数据库
            db.insert("taskinfo", null, cv);
            db.setTransactionSuccessful();
            db.endTransaction();
        }catch(Exception e){
            e.printStackTrace();
            DBHelper.closeDB(helper, db, null);
        }finally{
            DBHelper.closeDB(helper, db, null);
        }
    }   /**
     *  添加巡检任务事件
     *
     * @param bean
     */
    public  synchronized void inserttaskeven( TaskEvenBean bean) {
        try{
            db=helper.getReadableDatabase();
            db.beginTransaction();
            //生成ContentValues对象 //key:列名，value:想插入的值

//往ContentValues对象存放数据，键-值对模式
            ContentValues cv = ReflectUtil.getFields(bean);
//调用insert方法，将数据插入数据库
            db.insert("taskevent", null, cv);
            db.setTransactionSuccessful();
            db.endTransaction();
        }catch(Exception e){
            e.printStackTrace();
            DBHelper.closeDB(helper, db, null);
        }finally{
            DBHelper.closeDB(helper, db, null);
        }
    }  /**
     * 添加坐标点信息
     *
     * @param bean
     */
    public  synchronized void inserttasktrack(TrackBean bean) {
        try{
            db=helper.getReadableDatabase();
            db.beginTransaction();
            //生成ContentValues对象 //key:列名，value:想插入的值

//往ContentValues对象存放数据，键-值对模式
            ContentValues cv = ReflectUtil.getFields(bean);
//调用insert方法，将数据插入数据库
            db.insert("tasktrack", null, cv);
            db.setTransactionSuccessful();
            db.endTransaction();
        }catch(Exception e){
            e.printStackTrace();
            DBHelper.closeDB(helper, db, null);
        }finally{
            DBHelper.closeDB(helper, db, null);
        }
    } /**
     * 更新巡检记录信息
     * @return
     */
    public synchronized int updateTaskInfo_recodeid(String s_recode_id, double mileage,  String t_end){
        Cursor c=null;
        WalkBean b;
        int com=0;
//        String sql="update taskinfo set mileage = "+mileage+",t_end='"+t_end+"' where s_recode_id = '"+ s_recode_id+"'";
        try{
            db=helper.getReadableDatabase();
//            db.execSQL(sql);
            ContentValues updatedValues = new ContentValues();
            updatedValues.put("mileage", mileage);
            updatedValues.put("t_end", t_end);
            com= db.update("taskinfo", updatedValues,"s_recode_id = ?", new String[] { s_recode_id});
        }catch(Exception e){
            e.printStackTrace();
            DBHelper.closeDB(helper, db, c);
        }finally{
            DBHelper.closeDB(helper, db, c);
        }
        return com;
    }
    /**
     * 获取轨迹信息
     * @return
     */
    Gson g=new Gson();
    public synchronized List<TrackBean> findTaskTrack(String s_recode_id){
        Cursor c=null;
        String sql="select * from tasktrack where s_recode_id='"+s_recode_id+"'";
//        String sql="select * from tasktrack";
        ArrayList<TrackBean> beanList=new ArrayList<TrackBean>();
        TrackBean bean;
        try{
            db=helper.getReadableDatabase();
            c=db.rawQuery(sql, null);
            while(c!=null&&c.moveToNext()){
                bean=new TrackBean();
                bean.n_latx=c.getDouble(c.getColumnIndex("n_latx"));
                bean.n_lony=c.getDouble(c.getColumnIndex("n_lony"));
                bean.s_recode_id=c.getString(c.getColumnIndex("s_recode_id"));
                beanList.add(bean);
            }
        }catch(Exception e){
            e.printStackTrace();
            DBHelper.closeDB(helper, db, c);
        }finally{
            DBHelper.closeDB(helper, db, c);
        }
        return beanList;
    }
/**
     * 根据s_recode_id删除对应的巡检任务记录数据
     * @return
     */
    public synchronized int deleTaskInfo_recodeid(String s_recode_id){
        int i=0;
//        String sql="delete from taskinfo where s_recode_id ='"+s_recode_id+"'";
        try{
            db=helper.getReadableDatabase();


          i=  db.delete("taskinfo", "s_recode_id=?", new String[]{s_recode_id});
//         i= db.execSQL(sql);

        }catch(Exception e){
            e.printStackTrace();
            DBHelper.closeDB(helper, db, null);
        }finally{
            DBHelper.closeDB(helper, db, null);
        }
        return  i;
    }
    /**
     * 删除当天之前的巡检任务记录
     * @return
     */
    public synchronized void deleTaskTrack_datecur(int datecur){

        String sql="delete from tasktrack where datecur < "+datecur;//time 格式 20190505
        try{
            db=helper.getReadableDatabase();
//            db.delete("track", "trackid=?", new String[]{trackid});
            db.execSQL(sql);

        }catch(Exception e){
            e.printStackTrace();
            DBHelper.closeDB(helper, db, null);
        }finally{
            DBHelper.closeDB(helper, db, null);
        }
    }
    /**
     * 根据条件删除所有轨迹信息
     * @return
     */
    public synchronized void deleTaskTrack( ){
        String sql="delete from tasktrack";
        try{
            db=helper.getReadableDatabase();
            db.delete("tasktrack", null,null);
//             db.execSQL(sql);

        }catch(Exception e){
            e.printStackTrace();
            DBHelper.closeDB(helper, db, null);
        }finally{
            DBHelper.closeDB(helper, db, null);
        }
    }
}
