//package com.zzy.redis.lock.jedis.controller;
//
//import com.air.security.sodb.dts.core.base.Meta;
//import com.air.security.sodb.dts.core.config.FormatHandleSaveConfig;
//import com.air.security.sodb.dts.core.config.SqlModule;
//import com.air.security.sodb.dts.core.constant.MsgIdConstant;
//import com.air.security.sodb.dts.core.exception.SystemBaseException;
//import com.air.security.sodb.dts.core.spring.ApplicationContextUtil;
//import com.air.security.sodb.dts.core.util.CreateSqlUtil;
//import com.air.security.sodb.dts.core.util.DateTimeUtil;
//import com.air.security.sodb.dts.core.util.HaLog;
//import com.air.security.sodb.dts.core.util.UuidUtil;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.session.SqlSession;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Node;
//import org.dom4j.io.XMLWriter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.List;
//import java.util.Map;
//
///**
// * @description:航班数据保存Service实现
// **/
//@Service("WhFightMessageSaveServiceImpl")
//@Scope(scopeName = "prototype")
//public class WhFightMessageSaveServiceImpl {
//
//    private static final Logger log = LoggerFactory.getLogger(WhFightMessageSaveServiceImpl.class);
//
//    private static final String FLIGHT_PATH = "MSG/DFLT";
//
//    private static final String ARPTINE_PATH = "MSG/DFLT/AIRL/ARPT";
//
//    private static final String ENJOY_AIRPORT_PATH = "MSG/DFLT/SFLG";
//
//    private static final String GTLS_AIRPORT_PATH = "MSG/DFLT/GTLS/GATE";
//
//    private static final String BLLS_AIRPORT_PATH = "MSG/DFLT/BLLS/BELT";
//
//    private static final String FLIGHTID_PATH = "MSG/DFLT/FLID";
//
//    private static final String ABST_PATH = "MSG/DFLT/ABST";
//
//    private static final String ABRS_PATH = "MSG/DFLT/ABRS";
//
//    private static final String STND_PATH = "MSG/DFLT/STLS/STND";
//
//    private static final String CKLS_PATH = "MSG/DFLT/CKLS/CNTR";
//
//    private static final String ACDM_PATH = "MSG/FLTR";
//
//    private static final String AIRPORT_IATA = "WUH";
//
//    private static final String AIRPORT_ZHHH = "ZHHH";
//
//    private static final String AIRPORT_IATA_NAME = "武汉天河国际机场";
//
//    /**
//     * 业务处理
//     *
//     * @param meta
//     * @param jsonObject
//     * @throws SystemBaseException
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void execute(Meta meta, JSONObject jsonObject) throws SystemBaseException {
//        HaLog.info(log, MsgIdConstant.MS_INF_0001, meta.getEventType());
//
//        try {
//
//            // 获取消息格式化处理的配置
//            final FormatHandleSaveConfig config = super.pollFormatHandleRecvConfig();
//
//            // 操作数据库的sqlSession
//            SqlSession sqlSession = (SqlSession) ApplicationContextUtil.getBean(config.getSession());
//
//            String xmlMessage = jsonObject.getString("body");
//
//            HaLog.infoJson(log, xmlMessage);
//
//            Document document = DocumentHelper.parseText(xmlMessage);
//
//            // 数据处理操作
//            doExecute(meta, document, sqlSession, config);
//
//        } catch (SystemBaseException e) {
//            HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "");
//            throw e;
//        } catch (DocumentException e) {
//            HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "");
//        }
//
//        HaLog.info(log, MsgIdConstant.MS_INF_0002, meta.getEventType());
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param meta
//     * @param document
//     * @param document
//     */
//    public void doExecute(Meta meta, Document document, SqlSession sqlSession, FormatHandleSaveConfig config)
//            throws SystemBaseException {
//
//        // 航班数据json
//        JSONObject flightJson = new JSONObject();
//
//        Map<String, List<String>> mapping = config.getMapping();
//        List<String> columnList = mapping.get("column");
//        List<String> inputKeyList = mapping.get("inputKey");
//        List<String> typeList = mapping.get("type");
//
//        SqlModule sqlModule = new SqlModule();
//        sqlModule.setColumnList(columnList);
//        sqlModule.setKeyList(inputKeyList);
//        sqlModule.setTypeList(typeList);
//        sqlModule.setTableName(config.getTable());
//
//        //主键
//        flightJson.put("uuid", UuidUtil.getUuid36());
//
//        //航班动态增加信息
//        if ("DFIE".equals(meta.getEventType())
//                || "DFUE".equals(meta.getEventType())
//                || "DFDL".equals(meta.getEventType())) {
//
//            // 航班信息
//            Node flightData = document.selectSingleNode(FLIGHT_PATH);
//            flightData = (null != flightData) ? flightData : document.getRootElement();
//
//            // 航站信息
//            Node arptLineData = document.selectSingleNode(ARPTINE_PATH);
//            arptLineData = (null != arptLineData) ? arptLineData : document.getRootElement();
//
//            // 共享航班信息
//            Node enjoyAirport = document.selectSingleNode(ENJOY_AIRPORT_PATH);
//            enjoyAirport = (null != enjoyAirport) ? enjoyAirport : document.getRootElement();
//
//            //登机门信息
//            Node gltsAirport = document.selectSingleNode(GTLS_AIRPORT_PATH);
//            gltsAirport = (null != gltsAirport) ? gltsAirport : document.getRootElement();
//
//            //行李提取信息
//            Node bllsAirport = document.selectSingleNode(BLLS_AIRPORT_PATH);
//            bllsAirport = (null != bllsAirport) ? bllsAirport : document.getRootElement();
//
//            //值机柜台信息
//            Node cklsAirport = document.selectSingleNode(CKLS_PATH);
//            cklsAirport = (null != cklsAirport) ? cklsAirport : document.getRootElement();
//
//            //公共数据处理
//
//            Node planNode = arptLineData.selectSingleNode("FPTT");
//            if (planNode != null) {
//                String fexdTimeStringValue = planNode.getStringValue();
//
//                try {
//                    String flightScheduledDate = DateTimeUtil.parseDateStr(fexdTimeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//
//                    //航班计划日期
//                    flightJson.put("flightScheduledDate", flightScheduledDate.substring(0, 10));
//
//                    //航班计划时间
//                    flightJson.put("flightScheduledTime", flightScheduledDate);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "航班计划日期时间异常");
//                }
//            }
//
//            //航班区域(1：国内、2：国际、3：地区)
//            Node regionNode = arptLineData.selectSingleNode("APAT");
//            if (null != regionNode) {
//                String region = regionNode.getStringValue();
//                switch (region) {
//                    case "2403":
//                        flightJson.put("flightRegion", "1");
//                        break;
//                    case "2401":
//                        flightJson.put("flightRegion", "2");
//                        break;
//                    case "2402":
//                        flightJson.put("flightRegion", "3");
//                        break;
//                    default:
//                        flightJson.put("flightRegion", "4");
//                }
//            }
//
//            // 计划起飞时间
//            Node fpttNode = arptLineData.selectSingleNode("FPTT");
//            if (null != fpttNode) {
//                String fpttNodeStringValue = fpttNode.getStringValue();
//
//                try {
//                    String schedTakeOffDateTime = DateTimeUtil.parseDateStr(fpttNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("schedTakeOffDateTime", schedTakeOffDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "计划起飞时间为空");
//                }
//            }
//
//            //预计起飞时间
//            Node fettNode = arptLineData.selectSingleNode("FETT");
//            if (null != fettNode) {
//                String fettNodeStringValue = fettNode.getStringValue();
//
//                try {
//                    String estTakeOffDateTime = DateTimeUtil.parseDateStr(fettNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("estTakeOffDateTime", estTakeOffDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "预计起飞时间为空");
//                }
//            }
//
//            //实际起飞时间
//            Node fittNode = arptLineData.selectSingleNode("FRTT");
//            if (null != fittNode) {
//                String fittNodeStringValue = fittNode.getStringValue();
//
//                try {
//                    String actTakeOffDateTime = DateTimeUtil.parseDateStr(fittNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("actTakeOffDateTime", actTakeOffDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "实际起飞时间为空");
//                }
//            }
//
//            //计划落地时间
//            Node fpltNode = arptLineData.selectSingleNode("FPLT");
//            if (null != fpltNode) {
//                String fpltNodeStringValue = fpltNode.getStringValue();
//
//                try {
//                    String schedLandingDateTime = DateTimeUtil.parseDateStr(fpltNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("schedLandingDateTime", schedLandingDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "计划落地时间为空");
//                }
//
//            }
//
//            //预计落地时间
//            Node feltNode = arptLineData.selectSingleNode("FELT");
//            if (null != feltNode) {
//                String feltNodeStringValue = feltNode.getStringValue();
//
//                try {
//                    String estLandingDateTime = DateTimeUtil.parseDateStr(feltNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("estLandingDateTime", estLandingDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "预计落地日期为空");
//                }
//            }
//
//            //实际落地时间
//            Node frltNode = arptLineData.selectSingleNode("FRLT");
//            if (null != frltNode) {
//                String frltNodeStringValue = frltNode.getStringValue();
//
//                try {
//                    String actLandingDateTime = DateTimeUtil.parseDateStr(frltNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("actLandingDateTime", actLandingDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "实际落地日期为空");
//                }
//            }
//
//            //机场四字码
//            flightJson.put("airportIcaoCode", AIRPORT_ZHHH);
//
//            //登机开始时间
//            Node bortNode = flightData.selectSingleNode("BORT");
//            if (null != bortNode) {
//                String bortNodeStringValue = bortNode.getStringValue();
//
//                try {
//                    String boardingStartDateTime = DateTimeUtil.parseDateStr(bortNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("boardingStartDateTime", boardingStartDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "登机开始时间异常");
//                }
//            }
//
//            //催促登机时间
//            Node lbdtNode = flightData.selectSingleNode("LBDT");
//            if (null != lbdtNode) {
//                String lbdtNodeStringValue = lbdtNode.getStringValue();
//
//                try {
//                    String lastCallDateTime = DateTimeUtil.parseDateStr(lbdtNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("lastCallDateTime", lastCallDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "催促登机时间异常");
//                }
//            }
//
//            //登机结束时间
//            Node poktNode = flightData.selectSingleNode("POKT");
//            if (null != poktNode) {
//                String poktNodeStringValue = poktNode.getStringValue();
//
//                try {
//                    String boardingEndDateTime = DateTimeUtil.parseDateStr(poktNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("boardingEndDateTime", boardingEndDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "登机结束时间异常");
//                }
//            }
//
//            //航站楼
//            Node apNode = arptLineData.selectSingleNode("APNO");
//            if (null != apNode) {
//                String terminal = apNode.getStringValue();
//                flightJson.put("terminal", terminal);
//            }
//
//            //跑道号
//            Node runwayNumNode = flightData.selectSingleNode("RWAY");
//            if (null != runwayNumNode) {
//                String runwayNum = runwayNumNode.getStringValue();
//                flightJson.put("runwayNum", runwayNum);
//            }
//
//            //是否有vip旅客
//            Node hasVipNode = flightData.selectSingleNode("VIP");
//            if (null != hasVipNode) {
//                String hasVip = hasVipNode.getStringValue();
//                flightJson.put("hasVip", hasVip);
//            }
//
//            //登机口
//            Node gtnoNode = gltsAirport.selectSingleNode("GTNO");
//            if (null != gtnoNode) {
//                String gate = gtnoNode.getStringValue();
//                flightJson.put("gate", gate);
//            }
//
//            //登机门计划开始时间
//            Node estrNode = gltsAirport.selectSingleNode("ESTR");
//            if (null != estrNode) {
//                String nodeStringValue = estrNode.getStringValue();
//
//                try {
//                    String gateBeginTime = DateTimeUtil.parseDateStr(nodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("gateBeginTime", gateBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "登机门计划开始时间异常");
//                }
//            }
//
//            //登机门实际开始时间
//            Node rstrNode = gltsAirport.selectSingleNode("RSTR");
//            if (null != rstrNode) {
//                String rstrNodeStringValue = rstrNode.getStringValue();
//
//                try {
//                    String gateActBeginTime = DateTimeUtil.parseDateStr(rstrNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("gateActBeginTime", gateActBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "登机门实际开始时间异常");
//                }
//            }
//
//            //登机门计划结束时间
//            Node eendNode = gltsAirport.selectSingleNode("EEND");
//            if (null != eendNode) {
//                String eendNodeStringValue = eendNode.getStringValue();
//
//                try {
//                    String gateEndTime = DateTimeUtil.parseDateStr(eendNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("gateEndTime", gateEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "登机门计划结束时间异常");
//                }
//            }
//
//            //登机门实际结束时间
//            Node rendNode = gltsAirport.selectSingleNode("REND");
//            if (null != rendNode) {
//                String rendNodeStringValue = rendNode.getStringValue();
//
//                try {
//                    String gateActEndTime = DateTimeUtil.parseDateStr(rendNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("gateActEndTime", gateActEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "登机门实际结束时间异常");
//                }
//            }
//
//            //行李提取转盘号
//            Node btnoNode = bllsAirport.selectSingleNode("BTNO");
//            if (null != btnoNode) {
//                String baggageReclaimId = btnoNode.getStringValue();
//                flightJson.put("baggageReclaimId", baggageReclaimId);
//            }
//
//            //行李提取转盘计划开始时间
//            Node estrsNode = bllsAirport.selectSingleNode("ESTR");
//            if (null != estrsNode) {
//                String estrsNodeStringValue = estrsNode.getStringValue();
//
//                try {
//                    String baggageReclaimBeginTime = DateTimeUtil.parseDateStr(estrsNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("baggageReclaimBeginTime", baggageReclaimBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "行李提取转盘计划开始时间异常");
//                }
//            }
//
//            //行李提取转盘实际开始时间
//            Node rstrsNode = bllsAirport.selectSingleNode("RSTR");
//            if (null != rstrsNode) {
//                String rstrsNodeStringValue = rstrsNode.getStringValue();
//
//                try {
//                    String baggageActReclaimBeginTime = DateTimeUtil.parseDateStr(rstrsNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("baggageActReclaimBeginTime", baggageActReclaimBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "行李提取转盘实际开始时间异常");
//                }
//            }
//
//            //行李提取转盘计划结束时间
//            Node eendsNode = bllsAirport.selectSingleNode("EEND");
//            if (null != eendsNode) {
//                String eendsNodeStringValue = eendsNode.getStringValue();
//
//                try {
//                    String baggageReclaimEndTime = DateTimeUtil.parseDateStr(eendsNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("baggageReclaimEndTime", baggageReclaimEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "行李提取转盘计划结束时间异常");
//                }
//            }
//
//            //行李提取转盘实际结束时间
//            Node rendsNode = bllsAirport.selectSingleNode("REND");
//            if (null != rendsNode) {
//                String baggageValue = rendsNode.getStringValue();
//
//                try {
//                    String baggageActreclaimEndTime = DateTimeUtil.parseDateStr(baggageValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("baggageActreclaimEndTime", baggageActreclaimEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "行李提取转盘计划结束时间异常");
//                }
//            }
//
//            //值机柜台
//            Node codePortNode = cklsAirport.selectSingleNode("CODE");
//            if (null != codePortNode) {
//                String ckNodeStringValue = codePortNode.getStringValue();
//                flightJson.put("counter", ckNodeStringValue);
//            }
//
//            //值机柜台实际开始时间
//            Node rsStartNode = cklsAirport.selectSingleNode("RSTR");
//            if (null != rsStartNode) {
//                String rsNodeStringValue = rsStartNode.getStringValue();
//
//                try {
//                    String counterBeginTime = DateTimeUtil.parseDateStr(rsNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("counterBeginTime", counterBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "值机柜台实际开始时间异常");
//                }
//            }
//
//            //值机柜台实际结束时间
//            Node reOverNode = cklsAirport.selectSingleNode("REND");
//            if (null != reOverNode) {
//                String rsNodeStringValue = reOverNode.getStringValue();
//
//                try {
//                    String counterEndTime = DateTimeUtil.parseDateStr(rsNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("counterEndTime", counterEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "值机柜台实际结束时间异常");
//                }
//            }
//
//            //入库处理
//            sqlModule.setDataJson(flightJson);
//
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + flightJson.getString("sourceFlightId") + "'";
//            String updateSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            int i = sqlSession.insert("common.updateCommon", updateSql);
//            if (i < 1) {
//                String insertSql = CreateSqlUtil.createInsertSql(sqlModule);
//                sqlSession.insert("common.insertCommon", insertSql);
//            }
//        } else if ("DLYE".equals(meta.getEventType())) {
//
//            //航班延误
//            // 航班id
//            Node dlyeId = document.selectSingleNode(FLIGHTID_PATH);
//            if (StringUtils.isEmpty(dlyeId.getStringValue())) {
//                return;
//            }
//
//            //航班异常状态
//            Node flightAbrsstatus = document.selectSingleNode(ABST_PATH);
//            flightAbrsstatus = (null != flightAbrsstatus) ? flightAbrsstatus : document.getRootElement();
//
//            //异常状态信息
//            Node statusMassage = document.selectSingleNode(ABRS_PATH);
//            statusMassage = (null != statusMassage) ? statusMassage : document.getRootElement();
//
//            //入库处理
//            sqlModule.setDataJson(flightJson);
//
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + dlyeId.getStringValue() + "'";
//            String updateSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            sqlSession.insert("common.updateCommon", updateSql);
//        } else if ("CANE".equals(meta.getEventType())) {
//
//            //航班取消
//            // 航班id
//            Node cancelFlightId = document.selectSingleNode(FLIGHTID_PATH);
//            cancelFlightId = (null != cancelFlightId) ? cancelFlightId : document.getRootElement();
//            if (StringUtils.isEmpty(cancelFlightId.getStringValue())) {
//                return;
//            }
//
//            //航班异常状态
//            Node flightAbrsstatus = document.selectSingleNode(ABST_PATH);
//            flightAbrsstatus = (null != flightAbrsstatus) ? flightAbrsstatus : document.getRootElement();
//
//            //异常状态信息
//            Node statusMassage = document.selectSingleNode(ABRS_PATH);
//            statusMassage = (null != statusMassage) ? statusMassage : document.getRootElement();
//
//            //入库处理
//            sqlModule.setDataJson(flightJson);
//
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + cancelFlightId.getStringValue() + "'";
//            String updateSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            sqlSession.insert("common.updateCommon", updateSql);
//        } else if ("STLS".equals(meta.getEventType())) {
//
//            //航班机位信息
//            // 航班id
//            Node locationId = document.selectSingleNode(FLIGHTID_PATH);
//            locationId = (null != locationId) ? locationId : document.getRootElement();
//            if (StringUtils.isEmpty(locationId.getStringValue())) {
//                return;
//            }
//
//            //机位信息
//            Node stndNode = document.selectSingleNode(STND_PATH);
//            stndNode = (null != stndNode) ? stndNode : document.getRootElement();
//
//            //机位号
//            Node code = stndNode.selectSingleNode("CODE");
//            if (null != code) {
//                String currentStandId = code.getStringValue();
//                flightJson.put("currentStandId", currentStandId);
//            }
//
//            //机位计划开始时间
//            Node estr = stndNode.selectSingleNode("ESTR");
//            if (estr != null) {
//                String stringValue = estr.getStringValue();
//
//                try {
//                    String standBeginTime = DateTimeUtil.parseDateStr(stringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("standBeginTime", standBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "机位计划开始时间异常");
//                }
//            }
//
//            //机位实际开始时间
//            Node startNode = stndNode.selectSingleNode("RSTR");
//            if (null != startNode) {
//                String realStart = startNode.getStringValue();
//
//                try {
//                    String standActBeginTime = DateTimeUtil.parseDateStr(realStart,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("standActBeginTime", standActBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "机位实际开始时间异常");
//                }
//            }
//
//            //机位计划结束时间
//            Node eendNode = stndNode.selectSingleNode("EEND");
//            if (null != eendNode) {
//                String reNodeStringValue = eendNode.getStringValue();
//
//                try {
//                    String standEndTime = DateTimeUtil.parseDateStr(reNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("standEndTime", standEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "机位实际开始时间异常");
//                }
//            }
//
//            //机位实际结束时间
//            Node rendNode = stndNode.selectSingleNode("REND");
//            if (null != rendNode) {
//                String overTimeNode = rendNode.getStringValue();
//
//                try {
//                    String standActEndBeginTime = DateTimeUtil.parseDateStr(overTimeNode,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("standActEndBeginTime", standActEndBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "机位实际开始时间异常");
//                }
//            }
//
//            //入库处理
//            sqlModule.setDataJson(flightJson);
//
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + locationId.getStringValue() + "'";
//            String updateSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            sqlSession.insert("common.updateCommon", updateSql);
//        } else if ("FPTT".equals(meta.getEventType())) {
//
//            //航班计划时间变更
//            // 航班id
//            Node planId = document.selectSingleNode(FLIGHTID_PATH);
//            planId = (null != planId) ? planId : document.getRootElement();
//            if (StringUtils.isEmpty(planId.getStringValue())) {
//                return;
//            }
//
//            // 航班信息
//            Node flightData = document.selectSingleNode(FLIGHT_PATH);
//            flightData = (null != flightData) ? flightData : document.getRootElement();
//
//            //计划起飞时间
//            Node planTakeoffNode = flightData.selectSingleNode("FPTT");
//            if (null != planTakeoffNode) {
//                String planTakeoff = planTakeoffNode.getStringValue();
//
//                try {
//                    String schedTakeOffDateTime = DateTimeUtil.parseDateStr(planTakeoff,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("schedTakeOffDateTime", schedTakeOffDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "计划起飞时间异常");
//                }
//            }
//
//            //计划落地时间
//            Node planLandingNode = flightData.selectSingleNode("FPLT");
//            if (null != planLandingNode) {
//                String planLanding = planLandingNode.getStringValue();
//
//                try {
//                    String schedLandingDateTime = DateTimeUtil.parseDateStr(planLanding,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("schedLandingDateTime", schedLandingDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "计划落地时间异常");
//                }
//            }
//
//            //入库处理
//            sqlModule.setDataJson(flightJson);
//
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + planId.getStringValue() + "'";
//            String updateSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            sqlSession.insert("common.updateCommon", updateSql);
//        } else if ("CKLS".equals(meta.getEventType())) {
//
//            //航班值机柜台动态信息更新
//            // 航班信息
//            Node cklsData = document.selectSingleNode(CKLS_PATH);
//            cklsData = (null != cklsData) ? cklsData : document.getRootElement();
//
//            //id
//            Node cklsId = document.selectSingleNode(FLIGHTID_PATH);
//            cklsId = (null != cklsId) ? cklsId : document.getRootElement();
//            if (StringUtils.isEmpty(cklsId.getStringValue())) {
//                return;
//            }
//
//            //值机柜台
//            Node ckNode = cklsData.selectSingleNode("CODE");
//            if (null != ckNode) {
//                String ckNodeStringValue = ckNode.getStringValue();
//                flightJson.put("counter", ckNodeStringValue);
//            }
//
//            //值机柜台实际开始时间
//            Node rsNode = cklsData.selectSingleNode("RSTR");
//            if (null != rsNode) {
//                String rsNodeStringValue = rsNode.getStringValue();
//
//                try {
//                    String counterBeginTime = DateTimeUtil.parseDateStr(rsNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("counterBeginTime", counterBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "值机柜台实际开始时间异常");
//                }
//            }
//
//            //值机柜台实际结束时间
//            Node reNode = cklsData.selectSingleNode("REND");
//            if (null != reNode) {
//                String rsNodeStringValue = reNode.getStringValue();
//
//                try {
//                    String counterEndTime = DateTimeUtil.parseDateStr(rsNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("counterEndTime", counterEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "值机柜台实际结束时间异常");
//                }
//            }
//
//            //入库处理
//            sqlModule.setDataJson(flightJson);
//
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + cklsId.getStringValue() + "'";
//            String updateSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            sqlSession.insert("common.updateCommon", updateSql);
//        } else if ("DFDE".equals(meta.getEventType())) {
//
//            //动态航班删除
//            //id
//            Node dfdeId = document.selectSingleNode(FLIGHTID_PATH);
//            dfdeId = (null != dfdeId) ? dfdeId : document.getRootElement();
//            if (StringUtils.isEmpty(dfdeId.getStringValue())) {
//                return;
//            }
//
//            //删除处理
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + dfdeId.getStringValue() + "'";
//            String deleteSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            sqlSession.delete("common.deleteCommon", deleteSql);
//        } else if ("AIRL".equals(meta.getEventType())) {
//
//            //航班航线变更消息
//            // 航站信息
//            Node arptLineData = document.selectSingleNode(ARPTINE_PATH);
//            arptLineData = (null != arptLineData) ? arptLineData : document.getRootElement();
//
//            //航班id
//            Node airlId = document.selectSingleNode(FLIGHTID_PATH);
//            airlId = (null != airlId) ? airlId : document.getRootElement();
//            if (StringUtils.isEmpty(airlId.getStringValue())) {
//                return;
//            }
//
//            // 计划起飞时间
//            Node fpttNode = arptLineData.selectSingleNode("FPTT");
//            if (null != fpttNode) {
//                String fpttNodeStringValue = fpttNode.getStringValue();
//
//                try {
//                    String schedTakeOffDateTime = DateTimeUtil.parseDateStr(fpttNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("schedTakeOffDateTime", schedTakeOffDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "计划起飞时间为空");
//                }
//            }
//
//            //预计起飞时间
//            Node fettNode = arptLineData.selectSingleNode("FETT");
//            if (null != fettNode) {
//                String fettNodeStringValue = fettNode.getStringValue();
//
//                try {
//                    String estTakeOffDateTime = DateTimeUtil.parseDateStr(fettNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("estTakeOffDateTime", estTakeOffDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "预计起飞时间为空");
//                }
//            }
//
//            //实际起飞时间
//            Node fittNode = arptLineData.selectSingleNode("FRTT");
//            if (null != fittNode) {
//                String fittNodeStringValue = fittNode.getStringValue();
//
//                try {
//                    String actTakeOffDateTime = DateTimeUtil.parseDateStr(fittNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("actTakeOffDateTime", actTakeOffDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "实际起飞时间为空");
//                }
//            }
//
//            //计划落地时间
//            Node fpltNode = arptLineData.selectSingleNode("FPLT");
//            if (null != fpltNode) {
//                String fpltNodeStringValue = fpltNode.getStringValue();
//
//                try {
//                    String schedLandingDateTime = DateTimeUtil.parseDateStr(fpltNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("schedLandingDateTime", schedLandingDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "计划落地时间为空");
//                }
//            }
//
//            //预计落地时间
//            Node feltNode = arptLineData.selectSingleNode("FELT");
//            if (null != feltNode) {
//                String feltNodeStringValue = feltNode.getStringValue();
//
//                try {
//                    String estLandingDateTime = DateTimeUtil.parseDateStr(feltNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("estLandingDateTime", estLandingDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "预计落地日期为空");
//                }
//            }
//
//            //实际落地时间
//            Node frltNode = arptLineData.selectSingleNode("FRLT");
//            if (null != frltNode) {
//                String frltNodeStringValue = frltNode.getStringValue();
//
//                try {
//                    String actLandingDateTime = DateTimeUtil.parseDateStr(frltNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("actLandingDateTime", actLandingDateTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "实际落地日期为空");
//                }
//            }
//
//            //入库处理
//            sqlModule.setDataJson(flightJson);
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + airlId.getStringValue() + "'";
//            String updateSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            sqlSession.insert("common.updateCommon", updateSql);
//        } else if ("BLLS".equals(meta.getEventType())) {
//
//            //航班行李提取转盘动态信息更新
//            //行李提取信息
//            Node bllsAirport = document.selectSingleNode(BLLS_AIRPORT_PATH);
//            bllsAirport = (null != bllsAirport) ? bllsAirport : document.getRootElement();
//
//            //id
//            Node bllsId = document.selectSingleNode(FLIGHTID_PATH);
//            bllsId = (null != bllsId) ? bllsId : document.getRootElement();
//            if (StringUtils.isEmpty(bllsId.getStringValue())) {
//                return;
//            }
//
//            //行李提取转盘号
//            Node btnoNode = bllsAirport.selectSingleNode("BTNO");
//            if (null != btnoNode) {
//                flightJson.put("baggageReclaimId", btnoNode.getStringValue());
//            }
//
//            //行李提取转盘计划开始时间
//            Node estrsNode = bllsAirport.selectSingleNode("ESTR");
//            if (null != estrsNode) {
//                String stringValue = estrsNode.getStringValue();
//
//                try {
//                    String baggageReclaimBeginTime = DateTimeUtil.parseDateStr(stringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("baggageReclaimBeginTime", baggageReclaimBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "行李提取转盘实际结束时间异常");
//                }
//            }
//
//            //行李提取转盘实际开始时间
//            Node rstrsNode = bllsAirport.selectSingleNode("RSTR");
//            if (null != rstrsNode) {
//                String stringValue = rstrsNode.getStringValue();
//
//                try {
//                    String baggageActReclaimBeginTime = DateTimeUtil.parseDateStr(stringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("baggageActReclaimBeginTime", baggageActReclaimBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "行李提取转盘实际结束时间异常");
//                }
//            }
//
//            //行李提取转盘计划结束时间
//            Node eendsNode = bllsAirport.selectSingleNode("EEND");
//            if (null != eendsNode) {
//                String stringValue = eendsNode.getStringValue();
//
//                try {
//                    String baggageReclaimEndTime = DateTimeUtil.parseDateStr(stringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("baggageReclaimEndTime", baggageReclaimEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "行李提取转盘实际结束时间异常");
//                }
//            }
//
//            //行李提取转盘实际结束时间
//            Node rendsNode = bllsAirport.selectSingleNode("REND");
//            if (null != rendsNode) {
//                String stringValue = rendsNode.getStringValue();
//
//                try {
//                    String baggageActreclaimEndTime = DateTimeUtil.parseDateStr(stringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("baggageActreclaimEndTime", baggageActreclaimEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "行李提取转盘实际结束时间异常");
//                }
//            }
//
//            //入库处理
//            sqlModule.setDataJson(flightJson);
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + bllsId.getStringValue() + "'";
//            String updateSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            sqlSession.insert("common.updateCommon", updateSql);
//        } else if ("GTLS".equals(meta.getEventType())) {
//
//            //航班登机门动态信息更新
//            //登机门组信息
//            Node gtlsData = document.selectSingleNode(GTLS_AIRPORT_PATH);
//            gtlsData = (null != gtlsData) ? gtlsData : document.getRootElement();
//
//            //id
//            Node cklsId = document.selectSingleNode(FLIGHTID_PATH);
//            cklsId = (null != cklsId) ? cklsId : document.getRootElement();
//            if (StringUtils.isEmpty(cklsId.getStringValue())) {
//                return;
//            }
//
//            //登机口
//            Node gtnoNode = gtlsData.selectSingleNode("GTNO");
//            if (null != gtnoNode) {
//                String gate = gtnoNode.getStringValue();
//                flightJson.put("gate", gate);
//            }
//
//            //登机门计划开始时间
//            Node estrNode = gtlsData.selectSingleNode("ESTR");
//            if (null != estrNode) {
//                String nodeStringValue = estrNode.getStringValue();
//
//                try {
//                    String gateBeginTime = DateTimeUtil.parseDateStr(nodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("gateBeginTime", gateBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "登机门计划开始时间异常");
//                }
//            }
//
//            //登机门实际开始时间
//            Node rstrNode = gtlsData.selectSingleNode("RSTR");
//            if (null != rstrNode) {
//                String rstrNodeStringValue = rstrNode.getStringValue();
//
//                try {
//                    String gateActBeginTime = DateTimeUtil.parseDateStr(rstrNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("gateActBeginTime", gateActBeginTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "登机门实际开始时间异常");
//                }
//            }
//
//            //登机门计划结束时间
//            Node eendNode = gtlsData.selectSingleNode("EEND");
//            if (null != eendNode) {
//                String eendNodeStringValue = eendNode.getStringValue();
//
//                try {
//                    String gateEndTime = DateTimeUtil.parseDateStr(eendNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("gateEndTime", gateEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "登机门计划结束时间异常");
//                }
//            }
//
//            //登机门实际结束时间
//            Node rendNode = gtlsData.selectSingleNode("REND");
//            if (null != rendNode) {
//                String rendNodeStringValue = rendNode.getStringValue();
//
//                try {
//                    String gateActEndTime = DateTimeUtil.parseDateStr(rendNodeStringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("gateActEndTime", gateActEndTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "登机门实际结束时间异常");
//                }
//            }
//
//            //入库处理
//            sqlModule.setDataJson(flightJson);
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + cklsId.getStringValue() + "'";
//            String updateSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            sqlSession.insert("common.updateCommon", updateSql);
//        }
//
//        else if ("ACDMDFDL".equals(meta.getEventType())) {
//
//            //航班同步
//            Node acdmDfdlData = document.selectSingleNode(ACDM_PATH);
//            acdmDfdlData = (null != acdmDfdlData) ? acdmDfdlData : document.getRootElement();
//
//            //航班id
//            Node acdmId = document.selectSingleNode(FLIGHTID_PATH);
//            acdmId = (null != acdmId) ? acdmId : document.getRootElement();
//            if (null == acdmId || StringUtils.isBlank(acdmId.getStringValue())) {
//                throw new SystemBaseException("FlightId 航班id为空");
//            }
//
//            //预计上轮挡时间
//            Node etbtNode = acdmDfdlData.selectSingleNode("ETBT");
//            if (etbtNode != null) {
//                String stringValue = etbtNode.getStringValue();
//
//                try {
//                    String estOnBlockStringTime = DateTimeUtil.parseDateStr(stringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("estOnBlockStringTime", estOnBlockStringTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "预计上轮挡时间异常");
//                }
//            }
//
//            //实际上轮挡时间
//            Node aibtNode = acdmDfdlData.selectSingleNode("AIBT");
//            if (aibtNode != null) {
//                String stringValue = aibtNode.getStringValue();
//
//                try {
//                    String actOnBlockStringTime = DateTimeUtil.parseDateStr(stringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("actOnBlockStringTime", actOnBlockStringTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "实际上轮挡时间异常");
//                }
//            }
//
//            //预计撤轮挡时间
//            Node eobtNode = acdmDfdlData.selectSingleNode("EOBT");
//            if (eobtNode != null) {
//                String stringValue = eobtNode.getStringValue();
//
//                try {
//                    String estOffBlockStringTime = DateTimeUtil.parseDateStr(stringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("estOffBlockStringTime", estOffBlockStringTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "预计撤轮挡时间异常");
//                }
//            }
//
//            //实际撤轮挡时间
//            Node aobtNode = acdmDfdlData.selectSingleNode("AOBT");
//            if (aobtNode != null) {
//                String stringValue = aobtNode.getStringValue();
//
//                try {
//                    String actOffBlockStringTime = DateTimeUtil.parseDateStr(stringValue,
//                            "yyyyMMddhhmmss",
//                            "yyyy-MM-dd hh:mm:ss");
//                    flightJson.put("actOffBlockStringTime", actOffBlockStringTime);
//                } catch (ParseException e) {
//                    HaLog.error(log, MsgIdConstant.MS_ERR_0001, e, "预计撤轮挡时间异常");
//                }
//            }
//
//            //入库处理
//            sqlModule.setDataJson(flightJson);
//            String flightCondition = "SOURCE_FLIGHT_ID = '" + acdmId.getStringValue() + "'";
//            String updateSql = CreateSqlUtil.createUpdateSql(sqlModule, flightCondition);
//            sqlSession.insert("common.updateCommon", updateSql);
//        }
//    }
//
//    /**
//     * 公共数据处理
//     *
//     * @param flightData
//     * @param flightJson
//     * @param sqlSession
//     * @param enjoyAirport
//     */
//    private void makePublicFlightInfo(Node flightData, JSONObject flightJson, SqlSession sqlSession,
//                                      Node enjoyAirport) {
//
//        // 航班id
//        Node flightId = flightData.selectSingleNode("FLID");
//        if (null != flightId) {
//            flightJson.put("sourceFlightId", flightId.getStringValue());
//        }
//
//        // 航班号
//        Node flightNo = flightData.selectSingleNode("FLNO");
//        if(null !=flightNo){
//            flightJson.put("flightNo", flightNo.getStringValue());
//        }
//
//        // 航班方向（A、进港，D：离港）
//        Node flightIo = flightData.selectSingleNode("FLIO");
//        if (null != flightIo) {
//            flightJson.put("flightDirection", flightIo.getStringValue());
//        }
//
//        //航班日期
//        Node fexdNode = flightData.selectSingleNode("FEXD");
//
//        //航班标识
//        flightJson.put("flightId",
//                flightJson.getString("flightNo") + "-" + fexdNode.getStringValue() + "-" + flightIo.getStringValue());
//
//        // 关联航班id
//        Node flightIdNode = flightData.selectSingleNode("AFID");
//        if (null != flightIdNode) {
//            flightJson.put("associatedFlightId", flightIdNode.getStringValue());
//        } else {
//            flightJson.put("flightId",
//                    flightJson.getString("flightNo") + "-" + fexdNode.getStringValue() + "-" + flightIo.getStringValue());
//        }
//
//        //机场三字码
//        flightJson.put("airportIata", AIRPORT_IATA);
//        flightJson.put("airportIataCode", AIRPORT_IATA);
//
//        //机场名称
//        flightJson.put("airportName", AIRPORT_IATA_NAME);
//
//        //航空公司二字码
//        Node flightCode = flightData.selectSingleNode("AWCD");
//        if (null != flightCode) {
//            flightJson.put("airlineIataCode", flightCode.getStringValue());
//        }
//
//        //航班号全称
//        flightJson.put("flightIdentity", flightCode.getStringValue() + flightNo.getStringValue());
//
//        //共享航班号
//        List<Node> enjoyFlight = enjoyAirport.selectNodes("SFNO");
//        if (enjoyFlight != null && !enjoyFlight.isEmpty()) {
//            for (int i = 0; i < enjoyFlight.size(); i++) {
//                Node node = enjoyFlight.get(i);
//                flightJson.put("flightShareNo" + (i + 1), node.selectSingleNode("FLNO").getStringValue());
//            }
//        }
//
//        //飞机号
//        Node craftNoNode = flightData.selectSingleNode("CFNO");
//        if (null != craftNoNode) {
//            flightJson.put("aircraftNo", craftNoNode.getStringValue());
//        }
//
//    }
//}
//
