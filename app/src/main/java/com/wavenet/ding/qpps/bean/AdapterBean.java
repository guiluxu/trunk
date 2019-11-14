package com.wavenet.ding.qpps.bean;

import android.util.Log;

import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.wavenet.ding.qpps.utils.AppTool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoubeiwen on 2018/9/6.
 */

public class AdapterBean implements Serializable{
    public String mag1;
    public String mag2;
    public String mag3;
    public String mag4;
    public String url;
    public String urlJC;
    public String title;
    public String jingai_caizhi_type;
    public String guandao_caizhi_type;
    public Point mCenterPoint;
    public String type = "";

    public ObjectDetailsBean.FeaturesBean.GeometryBean mGeometry;
    public Graphic mG;//纠错时用的地图地址
    public int index;//纠错时用的地图图层
    public int OBJECTID;//纠错时用的地图图层
    public Geometry mFeaturesGeometry;//

    public static List<AdapterBean> getSeachPSJList(ObjectDetailsBean mBean,int resultid) {//0yu,1wu
        List<AdapterBean> list = new ArrayList<>();
        AdapterBean b;
        ObjectDetailsBean.FeaturesBean.AttributesBean fab;
        for (int i = 0; i < mBean.features.size(); i++) {
            b = new AdapterBean();
            b.mGeometry = mBean.features.get(i).geometry;
            fab = mBean.features.get(i).attributes;
            String mk1 = AppTool.getNullStr(fab.NMANHOLETYPE + "");
            String mv1 = "";
            if ("1".equals(mk1)) {
                mv1 = "雨水井:";
            } else if ("2".equals(mk1)) {
                mv1 = "污水井:";
            } else if ("3".equals(mk1)) {
                mv1 = "合流井:";
            }
            String mv2 = AppTool.getNullStr(fab.NMANHOLELENGTH + "");
            String mv3 = AppTool.getNullStr(fab.NMANHOLELENGTH + "");
            b.mag1 = mv1 + "φ" + mv2 ;
            String mk4 = AppTool.getNullStr(fab.NMANHOLESTATE + "");
            String mv4 = "";
            if ("0".equals(mk4)) {
                mv4 = "雨水口";
            } else if ("1".equals(mk4)) {
                mv4 = "检查井";
            } else if ("2".equals(mk4)) {
                mv4 = "接户井";
            } else if ("3".equals(mk4)) {
                mv4 = "闸阀井";
            } else if ("4".equals(mk4)) {
                mv4 = "溢流井:";
            } else if ("5".equals(mk4)) {
                mv4 = "倒虹井";
            } else if ("6".equals(mk4)) {
                mv4 = "透气井";
            } else if ("7".equals(mk4)) {
                mv4 = "压力井";
            } else if ("8".equals(mk4)) {
                mv4 = "检测井";
            } else if ("8".equals(mk4)) {
                mv4 = "其它井";
            }
            String mk5 = AppTool.getNullStr(fab.NMANHOLEGRADE + "");
            String mv5 = "";
            if ("1".equals(mk5)) {
                mv5 ="主井";
            }else if ("2".equals(mk5)){
                mv5 ="附井（接户井）";
            }else if ("3".equals(mk5)){
                mv5 ="附井（过度井）";
            }else if("4".equals(mk5)){
                mv5 = "附井（其它）";
            }else if ("5".equals(mk5)){
                mv5 = "附井（公共弄堂）";
            }else if ("6".equals(mk5)){
                mv5 ="附井(非小区管理)";
            }else{
                mv5 = "其他井";
            }
            b.mag2 = mv4+"-"+mv5;
            b.mag3 = AppTool.getNullStr(fab.SMANHOLENAMEROAD + "");
            b.url = AppTool.getNullStr(fab.SXTBM + "");
            b.urlJC = AppTool.getNullStr(fab.SMANHOLEID + "");
            b.title = mv1.replace(":","@");
            b.OBJECTID = fab.OBJECTID;
                b.index= resultid;
            String jingai_caizhi_type = AppTool.getNullStr(fab.NMANHOLEMATERIAL + "");
            String jingai_caizhi = "";
            if("1".equals(jingai_caizhi_type)){
                jingai_caizhi = "水泥";
            }else if("2".equals(jingai_caizhi_type)){
                jingai_caizhi = "铸铁";
            }else if("3".equals(jingai_caizhi_type)){
                jingai_caizhi = "复合材料";
            }else if("4".equals(jingai_caizhi_type)||"-1".equals(jingai_caizhi_type)){
                jingai_caizhi = "其他";
            }else {
                jingai_caizhi = "其他";
            }
            b.jingai_caizhi_type = "井盖制作材料："+jingai_caizhi;
            b.guandao_caizhi_type = null;
            list.add(b);
        }
        return list;
    }

    public static List<AdapterBean> getSeachPSGDList(ObjectDetailsBean mBean,int resultid) {//0yu,1wu
        List<AdapterBean> list = new ArrayList<>();
        AdapterBean b;
        ObjectDetailsBean.FeaturesBean.AttributesBean fab;
        for (int i = 0; i < mBean.features.size(); i++) {
            b = new AdapterBean();
            b.mGeometry = mBean.features.get(i).geometry;
            fab = mBean.features.get(i).attributes;
            String mk1 = AppTool.getNullStr(fab.NDRAIPIPETYPE + "");
            String mv1 = "";
            if ("1".equals(mk1)) {
                mv1 = "雨水管:";
            } else if ("2".equals(mk1)) {
                mv1 = "污水管:";
            } else if ("3".equals(mk1)) {
                mv1 = "合流管:";
            } else if ("9".equals(mk1)) {
                mv1 = "其他管:";
            }
            String mv2 = AppTool.getNullStr(fab.NDRAIPIPED1 + "");
            String mv3 = AppTool.getNullStr(fab.NDRAIPIPELENGTH + "");
            b.mag1 = mv1 + "φ" + mv2 + "-" + mv3;
            String mk4 = AppTool.getNullStr(fab.NDRAIPIPEGRADE + "");
            String mv4 = "";
            if ("1".equals(mk4)) {
                mv4 = "总干管";
            } else if ("2".equals(mk4)) {
                mv4 = "干管（截流管）";
            } else if ("3".equals(mk4)) {
                mv4 = "主管";
            } else if ("4".equals(mk4)) {
                mv4 = "连管";
            } else if ("5".equals(mk4)) {
                mv4 = "接户管";
            } else if ("6".equals(mk4)) {
                mv4 = "街坊管";
            } else if ("9".equals(mk4)) {
                mv4 = "其它管";
            }
            String mk5 = AppTool.getNullStr(fab.NDRAIPIPESTYLE + "");
            String mv5 = "";
            if ("1".equals(mk5)) {
                mv5 = "圆形";
            } else if ("2".equals(mk5)) {
                mv5 = "蛋形";
            } else if ("3".equals(mk5)) {
                mv5 = "矩形";
            } else if ("9".equals(mk5)) {
                mv5 = "其它";
            }
            b.mag2 = mv4 + "-" + mv5;
            String mv6, mv7, mv8;
            mv6 = AppTool.getNullStr(fab.SDRAIPIPENAMEROAD + "");
            mv7 = AppTool.getNullStr(fab.SDRAIPIPEBROADNAME + "");
            mv8 = AppTool.getNullStr(fab.SDRAIPIPEEROADNAME + "");
            b.mag3 = mv6 + "(" + mv7 + "-" + mv8 + ")";
            b.url = AppTool.getNullStr(fab.SXTBM + "");
            b.title = mv1.replace(":","#");
            b.OBJECTID = fab.OBJECTID;
            b.index= resultid;

            String guandao_caizhi_type = AppTool.getNullStr(fab.NDRAI_PIPEMATERIAL + "");
            String guandao_caizhi = "";
            if("1".equals(guandao_caizhi_type)){
                guandao_caizhi = "砼";
            }else if("2".equals(guandao_caizhi_type)){
                guandao_caizhi = "钢砼";
            }else if("3".equals(guandao_caizhi_type)){
                guandao_caizhi = "砖石";
            }else if("4".equals(guandao_caizhi_type)){
                guandao_caizhi = "塑料";
            }else if("9".equals(guandao_caizhi_type)){
                guandao_caizhi = "其他";
            }else {
                guandao_caizhi = "其他";
            }
            b.guandao_caizhi_type = "管道制作材料："+guandao_caizhi;
            b.jingai_caizhi_type = null;
            list.add(b);
        }
        return list;
    }

    public static List<AdapterBean> getSeachPFKList(ObjectDetailsBean mBean) {
        List<AdapterBean> list = new ArrayList<>();
        AdapterBean b;
        ObjectDetailsBean.FeaturesBean.AttributesBean fab;
        for (int i = 0; i < mBean.features.size(); i++) {
            b = new AdapterBean();
            b.mGeometry = mBean.features.get(i).geometry;
            fab = mBean.features.get(i).attributes;
            String mk1 = AppTool.getNullStr(fab.NMANHOLETYPE + "");
            String mv1 = "";
            if ("1".equals(mk1)) {
                mv1 = "雨水井:";
            } else if ("2".equals(mk1)) {
                mv1 = "污水井:";
            } else if ("3".equals(mk1)) {
                mv1 = "合流井:";
            }
            String mv2 = AppTool.getNullStr(fab.NMANHOLELENGTH + "");
            String mv3 = AppTool.getNullStr(fab.NMANHOLELENGTH + "");
            b.mag1 = mv1 + "φ" + mv2;
            String mk4 = AppTool.getNullStr(fab.NMANHOLESTATE + "");
            String mv4 = "";
            if ("0".equals(mk4)) {
                mv4 = "雨水口";
            } else if ("1".equals(mk4)) {
                mv4 = "检查井";
            } else if ("2".equals(mk4)) {
                mv4 = "接户井";
            } else if ("3".equals(mk4)) {
                mv4 = "闸阀井";
            } else if ("4".equals(mk4)) {
                mv4 = "溢流井:";
            } else if ("5".equals(mk4)) {
                mv4 = "倒虹井";
            } else if ("6".equals(mk4)) {
                mv4 = "透气井";
            } else if ("7".equals(mk4)) {
                mv4 = "压力井";
            } else if ("8".equals(mk4)) {
                mv4 = "检测井";
            } else if ("9".equals(mk4)) {
                mv4 = "其它井";
            }
            String mk5 = AppTool.getNullStr(fab.NMANHOLEGRADE + "").trim();
            String mv5 = "";
            if ("1".equals(mk5)) {
                mv5 = "主井";
            } else if ("2".equals(mk5)) {
                mv5 = "附井（接户井）";
            } else if ("3".equals(mk5)) {
                mv5 = "附井（过度井）";
            } else if ("4".equals(mk5)) {
                mv5 = "附井（其它）";
            } else if ("5".equals(mk5)) {
                mv5 = "附井（公共弄堂）";
            } else if ("6".equals(mk5)) {
                mv5 = "附井（非小区管理的）";
            }
            b.mag2 = mv4+"-"+mv5;
            b.mag3 = AppTool.getNullStr(fab.SMANHOLENAMEROAD + "");
            b.url = AppTool.getNullStr(fab.SXTBM + "");
            b.title = "排放口";
            b.OBJECTID = fab.OBJECTID;

            String jingai_caizhi_type = AppTool.getNullStr(fab.NMANHOLEMATERIAL + "");
            String jingai_caizhi = "";
            if("1".equals(jingai_caizhi_type)){
                jingai_caizhi = "水泥";
            }else if("2".equals(jingai_caizhi_type)){
                jingai_caizhi = "铸铁";
            }else if("3".equals(jingai_caizhi_type)){
                jingai_caizhi = "复合材料";
            }else if("4".equals(jingai_caizhi_type)||"-1".equals(jingai_caizhi_type)){
                jingai_caizhi = "其他";
            }else {
                jingai_caizhi = "其他";
            }
            b.jingai_caizhi_type = "井盖制作材料："+jingai_caizhi;
            b.guandao_caizhi_type = null;

            list.add(b);
        }
        return list;
    }

    public static List<AdapterBean> getSeachPSBZList(ObjectDetailsBean mBean) {
        List<AdapterBean> list = new ArrayList<>();
        AdapterBean b;
        ObjectDetailsBean.FeaturesBean.AttributesBean fab;
        for (int i = 0; i < mBean.features.size(); i++) {
            b = new AdapterBean();
            b.mGeometry = mBean.features.get(i).geometry;
            fab = mBean.features.get(i).attributes;
            b.mag1 = AppTool.getNullStr(fab.SDRAIPUMPNAME + "");
            String mk1 = AppTool.getNullStr(fab.NDRAIPUMPTYPE + "");
            String mk2 = AppTool.getNullStr(fab.NDRAIPUMPTYPEFEAT + "").trim();
            String mv1 = "";
            String mv2 = "";
            if ("1".equals(mk1)) {
                mv1 = "雨水";
            } else if ("2".equals(mk1)) {
                mv1 = "污水";
            } else if ("3".equals(mk1)) {
                mv1 = "合建";
            } else if ("9".equals(mk1)) {
                mv1 = "其它";
            }
//                                分流污水，6-合流截流，7-干线输送，8-分流，9-合流，10-其他
            if ("1".equals(mk2)) {
                mv2 = "分流雨水";
            } else if ("2".equals(mk2)) {
                mv2 = "合流防汛";
            } else if ("3".equals(mk2)) {
                mv2 = "立交";
            } else if ("4".equals(mk2)) {
                mv2 = "闸泵";
            } else if ("5".equals(mk2)) {
                mv2 = "分流污水";
            } else if ("6".equals(mk2)) {
                mv2 = "合流截流";
            } else if ("7".equals(mk2)) {
                mv2 = "干线输送";
            } else if ("8".equals(mk2)) {
                mv2 = "分流";
            } else if ("9".equals(mk2)) {
                mv2 = "合流";
            } else if ("10".equals(mk2)) {
                mv2 = "其他";
            }
            b.mag2 = mv1 + "-" + mv2;
            b.mag3 = AppTool.getNullStr(fab.SDRAIPUMPADD + "");
            b.url = AppTool.getNullStr(fab.SXTBM + "");
            b.title = "排水泵站";
            b.OBJECTID = fab.OBJECTID;
            b.guandao_caizhi_type = null;
            b.jingai_caizhi_type = null;
            list.add(b);
        }
        return list;
    }

    public static List<AdapterBean> getSeachWSCLCList(ObjectDetailsBean mBean) {
        List<AdapterBean> list = new ArrayList<>();
        AdapterBean b;
        ObjectDetailsBean.FeaturesBean.AttributesBean fab;
        for (int i = 0; i < mBean.features.size(); i++) {
            b = new AdapterBean();
            b.mGeometry = mBean.features.get(i).geometry;
            fab = mBean.features.get(i).attributes;
            b.mag1 = AppTool.getNullStr(fab.SFACTNAME + "");
            String mv1 = AppTool.getNullStr(fab.NFACTCAPDSN + "");
            String mv2 = AppTool.getNullStr(fab.SFACTMETHODTREAT + "");
            b.mag2 = "";
            b.mag3 = AppTool.getNullStr(fab.SFACTADD + "");
            b.url = AppTool.getNullStr(fab.SFACTID + "");
            b.title = "污水处理厂";
            b.OBJECTID = fab.OBJECTID;
            b.guandao_caizhi_type = null;
            b.jingai_caizhi_type = null;
            list.add(b);
        }
        return list;
    }

    public static List<AdapterBean> getSeachPSHList(ObjectDetailsBean mBean) {
        List<AdapterBean> list = new ArrayList<>();
        AdapterBean b;
        ObjectDetailsBean.FeaturesBean.AttributesBean fab;
        for (int i = 0; i < mBean.features.size(); i++) {
            b = new AdapterBean();
            b.mGeometry = mBean.features.get(i).geometry;
            fab = mBean.features.get(i).attributes;
            b.mag1 = AppTool.getNullStr(fab.sName + "");
            String mv1 = AppTool.getNullStr(fab.sTown + "");
            String mv2 = AppTool.getNullStr(fab.sType + "");
            b.mag2 = mv1 + "-" + mv2;
//            String mv3 = AppTool.getNullStr(fab.sTown);
            String mv4 = AppTool.getNullStr(fab.sAddress + "");
            b.mag3 =  mv4;
            b.url = AppTool.getNullStr(fab.QPPSHTABLEID + "");
            b.title = "排水户";
            b.OBJECTID = fab.OBJECTID;
            b.guandao_caizhi_type = null;
            b.jingai_caizhi_type = null;
            list.add(b);
        }
        return list;
    }

    public static List<AdapterBean> getSeachZDPFKList(ObjectDetailsBean mBean) {
        List<AdapterBean> list = new ArrayList<>();
        AdapterBean b;
        ObjectDetailsBean.FeaturesBean.AttributesBean fab;
        for (int i = 0; i < mBean.features.size(); i++) {
            b = new AdapterBean();
            b.mGeometry = mBean.features.get(i).geometry;
            fab = mBean.features.get(i).attributes;
            b.mag1 = AppTool.getNullStr(fab.SOutletNAME + "");
//            String mv1 = AppTool.getNullStr(fab.SSYSNAME + "");
//            String mv2 = AppTool.getNullStr(fab.SPairufangxiang + "");
//            b.mag2 = mv1 + "-" + mv2;
            b.mag3 =  AppTool.getNullStr(fab.SManageUnit + "");
            b.mag2 = AppTool.getNullStr(fab.SPairufangxiang + "");
            b.url = AppTool.getNullStr(fab.SOUTLETID + "");
            b.title = "重点排放口";
            b.OBJECTID = fab.OBJECTID;
            b.guandao_caizhi_type = null;
            b.jingai_caizhi_type = null;
            list.add(b);
        }
        return list;
    }
}
