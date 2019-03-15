package com.saiyi.oldmanwatch.helper;

import com.saiyi.oldmanwatch.entity.AddDeviceBean;
import com.saiyi.oldmanwatch.entity.AddEnclosure;
import com.saiyi.oldmanwatch.entity.AddOpinionBean;
import com.saiyi.oldmanwatch.entity.AddReimnd;
import com.saiyi.oldmanwatch.entity.AddSetupBean;
import com.saiyi.oldmanwatch.entity.AddSosBean;
import com.saiyi.oldmanwatch.entity.BodyTrend;
import com.saiyi.oldmanwatch.entity.ContactsBean;
import com.saiyi.oldmanwatch.entity.ContactsListBean;
import com.saiyi.oldmanwatch.entity.ControlDeviceBean;
import com.saiyi.oldmanwatch.entity.DelDevice;
import com.saiyi.oldmanwatch.entity.DeviceDetailsBean;
import com.saiyi.oldmanwatch.entity.DeviceState;
import com.saiyi.oldmanwatch.entity.DeviceinfoList;
import com.saiyi.oldmanwatch.entity.Enclosures;
import com.saiyi.oldmanwatch.entity.Hearts;
import com.saiyi.oldmanwatch.entity.HeartsList;
import com.saiyi.oldmanwatch.entity.LoginBean;
import com.saiyi.oldmanwatch.entity.ModelSetBean;
import com.saiyi.oldmanwatch.entity.NoticeList;
import com.saiyi.oldmanwatch.entity.QueryDevice;
import com.saiyi.oldmanwatch.entity.QueryDeviceDetailsBean;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.entity.QueryHeartsList;
import com.saiyi.oldmanwatch.entity.QueryRemindList;
import com.saiyi.oldmanwatch.entity.QueryStepBean;
import com.saiyi.oldmanwatch.entity.QueryTrendBena;
import com.saiyi.oldmanwatch.entity.RegisterBean;
import com.saiyi.oldmanwatch.entity.ReimndList;
import com.saiyi.oldmanwatch.entity.RequestApplyBind;
import com.saiyi.oldmanwatch.entity.RequestLogin;
import com.saiyi.oldmanwatch.entity.RequestRegister;
import com.saiyi.oldmanwatch.entity.SetupBean;
import com.saiyi.oldmanwatch.entity.SosPhoneBean;
import com.saiyi.oldmanwatch.entity.StepBean;
import com.saiyi.oldmanwatch.entity.SwitchSetBean;
import com.saiyi.oldmanwatch.entity.Trajectory;
import com.saiyi.oldmanwatch.entity.UpdateDeviceBean;
import com.saiyi.oldmanwatch.entity.UpdateDeviceState;
import com.saiyi.oldmanwatch.entity.UpdateEnclosureBean;
import com.saiyi.oldmanwatch.entity.UpdatePWD;
import com.saiyi.oldmanwatch.entity.UpdateUserBean;
import com.saiyi.oldmanwatch.entity.UserBean;
import com.saiyi.oldmanwatch.entity.WeightTrends;
import com.saiyi.oldmanwatch.entity.bodyUserListBean;
import com.saiyi.oldmanwatch.http.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @Author liwenbo
 * @Date 18/9/14
 * @Describe Retrofit网络请求接口
 */
public interface RetrofitService<T> {



     String  MAC ="MAC_ADDRESS";

    @POST("/ElderlyWatch/app/user/register")
    Observable<BaseResponse<RegisterBean>> register(@Body RequestRegister data);

    @POST("/ElderlyWatch/app/user/login")
    Observable<BaseResponse<LoginBean>> login(@Body RequestLogin data);

    @GET("/ElderlyWatch/app/user/queryPhone")
    Observable<BaseResponse<Void>> queryPhone(@Query("phone") String phone);

    @POST("/ElderlyWatch/app/device/queryDeviceDetails")
    Observable<BaseResponse<DeviceDetailsBean>> queryDeviceDetails(@Body QueryDeviceDetailsBean data, @Query("token") String token);

    @Multipart
    @POST("/ElderlyWatch/app/user/uploadImg")
    Observable<BaseResponse<String>> uploadImg(@Part MultipartBody.Part file);

    @POST("/ElderlyWatch/app/user/updateUser")
    Observable<BaseResponse<Void>> updateUser(@Body UpdateUserBean data, @Query("token") String token);

    @GET("/ElderlyWatch/app/user/queryUser")
    Observable<BaseResponse<UserBean>> queryUser(@Query("uid") String uid, @Query("token") String token);

    @POST("/ElderlyWatch/app/user/addOpinion")
    Observable<BaseResponse<Void>> addOpinion(@Body AddOpinionBean data);

    @POST("/ElderlyWatch/app/contacts/addContacts")
    Observable<BaseResponse<Void>> addContacts(@Body ContactsBean data, @Query("token") String token);

    @POST("/ElderlyWatch/app/contacts/contactsList")
    Observable<BaseResponse<List<ContactsListBean>>> getContactsList(@Query("mac") String mac, @Query("token") String token);

    @POST("/ElderlyWatch/app/device/addDevice")
    Observable<BaseResponse<Void>> addDevice(@Body AddDeviceBean data, @Query("token") String token);

    @POST("/ElderlyWatch/app/device/updateDevice")
    Observable<BaseResponse<Void>> updateDevice(@Body UpdateDeviceBean data, @Query("token") String token);

    @POST("/ElderlyWatch/app/device/applicationBindingDevice")
    Observable<BaseResponse<Void>> applyBind(@Body RequestApplyBind data, @Query("token") String token);


    @GET("/ElderlyWatch/app/device/queryDevice")
    Observable<BaseResponse<QueryDevice>> queryDevice(@Query("mac") String mac, @Query("token") String token);

    @GET("/ElderlyWatch/app/device/queryDeviceList")//111
    Observable<BaseResponse<List<QueryDeviceList>>> queryDeviceList(@Query("uid") int uid, @Query("token") String token);





    @POST("/ElderlyWatch/app/device/queryHeartsList")
    Observable<BaseResponse<List<HeartsList>>> queryHeartsList(@Query("mac") String mac, @Query("startDate") String startDate, @Query("endDate") String endDate);

    @GET("/ElderlyWatch/app/user/getCode")
    Observable<BaseResponse<Void>> getCode(@Query("phone") String phone);

    @POST("/ElderlyWatch/app/user/updatePassword")
    Observable<BaseResponse<Void>> updatePassword(@Body UpdatePWD data);

    /**
     * 添加提醒
     *
     * @param data
     * @return
     */
    @POST("/ElderlyWatch/app/device/addReimnd")
    Observable<BaseResponse<Void>> addReimnd(@Body AddReimnd data);

    @POST("/ElderlyWatch/app/device/queryReimndList")
    Observable<BaseResponse<List<ReimndList>>> queryReimndList(@Body QueryRemindList data, @Query("token") String token);

    @GET("/ElderlyWatch/app/device/deleteReimnd")
    Observable<BaseResponse<Void>> delRemind(@Query("id") int id, @Query("token") String token);

    @POST("/ElderlyWatch/app/device/addSetup")
    Observable<BaseResponse<Void>> addSetup(@Body AddSetupBean data, @Query("token") String token);

    @GET("/ElderlyWatch/app/device/querySetupList")
    Observable<BaseResponse<List<SetupBean>>> querySetupList(@Query("mac") String mac, @Query("token") String token);

    @POST("/ElderlyWatch/app/device/updateSetup")
    Observable<BaseResponse<Void>> updateSetup(@Body SwitchSetBean data, @Query("token") String token);

    @POST("/ElderlyWatch/app/device/updateModelSet")
    Observable<BaseResponse<Void>> updateModelSet(@Body ModelSetBean data, @Query("token") String token);

    @GET("/ElderlyWatch/app/contacts/querySosList")
    Observable<BaseResponse<List<SosPhoneBean>>> getSosList(@Query("mac") String mac, @Query("token") String token);

    @POST("/ElderlyWatch/app/contacts/setSos")
    Observable<BaseResponse<Void>> setSos(@Body AddSosBean data, @Query("token") String token);

    @GET("/ElderlyWatch/app/device/deviceinfoList")
    Observable<BaseResponse<List<DeviceinfoList>>> getDeviceList(@Query("mac") String mac, @Query("token") String token);

    @POST("/ElderlyWatch/app/device/updateState")
    Observable<BaseResponse<Void>> updateState(@Body UpdateDeviceState data, @Query("token") String token);

    @GET("/ElderlyWatch/app/device/deleteDeviceinfo")
    Observable<BaseResponse<Void>> delDevice(@Query("token") String token, @Query("id") int id);

    @POST("/ElderlyWatch/app/device/deleteDevice")
    Observable<BaseResponse<Void>> delMyDevice(@Body DelDevice data, @Query("token") String token);

    @GET("/ElderlyWatch/app/device/queryMessageList")
    Observable<BaseResponse<List<NoticeList>>> getNoticeList(@Query("mac") String mac, @Query("token") String token);

    @GET("/ElderlyWatch/app/device/deleteMessage")
    Observable<BaseResponse<Void>> delNotice(@Query("token") String token, @Query("id") int id);

    @POST("/ElderlyWatch/app/device/queryStep")
    Observable<BaseResponse<StepBean>> getStep(@Body QueryStepBean data);

    @GET("/ElderlyWatch/app/device/queryHearts")
    Observable<BaseResponse<Hearts>> getHearts(@Query("mac") String mac);

    @POST("/ElderlyWatch/app/device/addEnclosure")
    Observable<BaseResponse<Void>> addEnclosure(@Body AddEnclosure data, @Query("token") String token);

    @POST("/ElderlyWatch/app/device/updateEnclosure")
    Observable<BaseResponse<Void>> updateEnclosure(@Body UpdateEnclosureBean data, @Query("token") String token);

    @GET("/ElderlyWatch/app/device/queryEnclosures")
    Observable<BaseResponse<Enclosures>> getEnclosures(@Query("mac") String mac);

    @POST("/ElderlyWatch/app/tcp/sendCmDevice")
    Observable<BaseResponse<Void>> sendCmDevice(@Body ControlDeviceBean data);

    @GET("/ElderlyWatch/app/device/queryDeviceState")
    Observable<BaseResponse<DeviceState>> getDeviceState(@Query("mac") String mac);

    @POST("/ElderlyWatch/app/device/queryTrajectory")
    Observable<BaseResponse<List<Trajectory>>> getTraJectory(@Body QueryHeartsList data, @Query("token") String token);

    /**
     *  电子秤模块
     * */
    @GET("/ElderlyWatch/app/user/queryUserParameter")
    Observable<BodyTrend>queryBodyTrend(@Query("uid") int uid);
      //查询用户参数(称)
     String QUERYUSERPARAMETER = "ElderlyWatch/app/user/queryUserParameter?uid=";

     String QUERYPARAMETERLIST = "ElderlyWatch/app/user/queryParameterList";


     String WEIGHT_USER_PARAMETER = "ElderlyWatch/app/user/queryUserParameterByDate";

    //查用称的成员列表
     String QUERY_USER_LIST= "ElderlyWatch/app/user/queryMemberList?uid=";


     String ADD_USER= "ElderlyWatch/app/user/addUserMember";

     //查询设备用户列表
     String QUERY_DEVICE_USER_LIST ="ElderlyWatch/app/user/queryUserListByMac?mac=";

     //移交管理员（称）
     String  HAND_OVER_MANAGER  ="ElderlyWatch/app/user/updateUserRole";

     // 解除绑定设备

    String UNBINDDEVICE = "ElderlyWatch/app/user/delDevice";

    // 查询用户类型（称）
    String QUERY_USER_TYPE="ElderlyWatch/app/user/queryUserType";

    //查询某一种设备列表（称）
    String ONLY_DEVICE_LIST= "ElderlyWatch/app/device/queryDeviceLists";

    //查询个人信息
    String USER_INFO= "ElderlyWatch/app/user/queryUser?uid=";

    //添加测量数据
    String ADD_TEST_DATE = "ElderlyWatch/app/user/addUserParameter";

    String QUERY_ALL_DEVICE_LIST=  "ElderlyWatch/app/device/queryDeviceList?uid=";






    //查询身体趋势（称）
     @POST("ElderlyWatch/app/user/queryUserParameterByDate")
     Observable<List<WeightTrends>>queryWeighetChart(@Body QueryTrendBena data, @Query("token") String token);


    /**
     *  电子秤模块
     * */
    @GET("/ElderlyWatch/app/user/queryMemberList")
    Observable<BaseResponse<List<bodyUserListBean>>>queryUserList(@Query("uid") int uid , @Query("token") String token);






}
