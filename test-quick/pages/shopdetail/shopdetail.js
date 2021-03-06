const date = new Date()
//年  
var Y = date.getFullYear();
//月  
var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
//日  
var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
//时  
var h = date.getHours();
//分  
var m = date.getMinutes();
//秒  
var s = date.getSeconds();

const days = ["今天","明天"]
const hours = []
const hours_t = []
const minutes = []
const minutes_t = ['0','5', '10', '15', '20', '25', '30', '35', '45', '50', '55']


for (let i = 1; i <= 23; i++) {
  hours_t.push(i)
  if(i>=h){
    hours.push(i)
  }
}
for (let i = 0; i <11; i++) {
  if (m > 55){
    minutes.push(0)
    break
  }
  if (minutes_t[i]>m)
    minutes.push(minutes_t[i])
    
}


Page({
  data: {
    imgUrls: [],    
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 300,
    showtime: "none",
    hideOther: "block",
    day:"今天",
    days: days,
    hour:h,
    hours: hours,
    minute:m,
    minutes: minutes,
    people:1,
    phone:"",
    remark:"",
    shopPhone:"",
    longitude: "",
    latitude: "",
    shop:null,
  },
  bindChange: function (e) {
    const val = e.detail.value
    if (val[0] > 0||val[1] > 0) {
      if (val[0] > 0){
        this.setData({ hours: hours_t })
      }else{
        this.setData({ hours: hours })
      }
      this.setData({ minutes: minutes_t })
    }else{
      this.setData({ minutes: minutes })
      this.setData({ hours: hours })
    }
    this.setData({
      day: this.data.days[val[0]],
      hour: this.data.hours[val[1]],
      minute: this.data.minutes[val[2]]
    })
  },
  showtime: function () {
    this.setData({
      showtime: "block",
      hideOther:"none"
    })
  }, 
  addP: function () {
    this.setData({
      people: this.data.people + 1
    })
  },
  subP: function () {
    if (this.data.people>1){
    this.setData({
      people: this.data.people - 1
    })
    }
  },
  canceltime: function () {
    this.setData({ showtime: "none", hideOther: "block" })
    
    
   },
  comfirmtime: function () {
    this.setData({ showtime: "none", hideOther: "block" })
  }, 
  bindPhone: function (e) {
    this.setData({
      phone: e.detail.value
    })
  },
  bindTextAreaBlur: function (e) {
    this.setData({
      remark: e.detail.value
    })
  },
  //预览图片
  previewImg:function (e) {
    var index = e.currentTarget.dataset.index;
    var imgArr = this.data.imgUrls;
    wx.previewImage({
      current: imgArr[index],     //当前图片地址
      urls: imgArr,               //所有要预览的图片的地址集合 数组形式
      success: function (res) { },
      fail: function (res) { },
      complete: function (res) { },
    })
  },
  //打电话
  call: function () {
    wx.makePhoneCall({
      phoneNumber: this.data.shop.phone
    })
  },
  //导航
  goto:function(){
    console.log(this.data.shop)
    wx.openLocation({
      latitude: this.data.shop.latitude, 
      longitude: this.data.shop.longitude, 
      name: this.data.shop.shopName, 
      scale: 29
    })
  },
  //菜单
  toMenu : function(){
    console.log(this.data.shopId)
    wx.navigateTo({
      url: '../../pages/food/food?shopId=' + this.data.shopId + '&imgUrls=' + this.data.shop.menuImage
    })
  },
  //预订
  comfirorder: function (e) {
    if (!wx.getStorageSync("openid")) {
      getApp().getUserInfo();
    }    
    if (!this.data.phone){
      wx.showToast({
       title: '请填写预留手机号码',
       icon:"none",
       duration: 2000
      })
      return false;
    }else{
      var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
      if (!myreg.test(this.data.phone)) {
        wx.showToast({
          title: '请填写正确的手机号码',
          icon: "none",
          duration: 2000
        })
        return false;
      }
    }
    var date = this.data.day + this.data.hour + "时" + this.data.minute;
    var people = this.data.people;
    var phone = this.data.phone;
    var remark = this.data.remark;
    var shopId = this.data.shopId;
    wx.showModal({
      title: this.data.phone,
      content: '您' + people + '人将于' + date + "分到店，备注:" + remark,
      success: function (res) {
        if (res.confirm) {
          wx.showLoading({
            title: '预约中',
          })
          console.log(e.detail.formId)
          wx.request({
            url: getApp().data.servsers+'/comfirorder', //仅为示例，并非真实的接口地址
            data: {
              arriveTime: date, phone: phone, people: people, remark: remark,shopId:shopId
              , openId: wx.getStorageSync("openid"), formId: e.detail.formId
            },
            method: 'POST',
            header: {
              'content-type': 'application/x-www-form-urlencoded;charset=utf-8',
            },
            success: function (res) {
              if(res.data){
                wx.hideLoading()
                wx.showToast({
                  title: '预约成功',
                  icon: 'success',
                  duration: 2000
                })
                //===========
                setTimeout(function(){wx.redirectTo ({
                  url:"../myorder/myorder"
                })}
                ,2000)
              }
            }
          })
      }
    }
    })
  },
  onShareAppMessage: function (options) {
    var that = this;
    var shareObj = {
      title: that.data.shopName,
      success: function (res) { },
      fail: function () { },
    }
    return shareObj;
  },
  getDetail: function (shopId){
    var that = this;
    wx.request({
      url: getApp().data.servsers + '/getShopByPage', //仅为示例，并非真实的接口地址
      data: {
        id: shopId,
      },
      method: 'get',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data[0])
        that.data.imgUrls.push(res.data[0].mainImage)
        var slide = res.data[0].slideImage.split(",");
        for (var i = 0; i < slide.length;i++){
          that.data.imgUrls.push(slide[i])
        }
        that.setData({
          shopId: res.data[0].id,
          shop: res.data[0],
          imgUrls: that.data.imgUrls,
        });
        wx.setNavigationBarTitle({
          title: res.data[0].shopName
        })
      }
    })
    that.setData({
      shopId: shopId
    })
  },
  onLoad: function (options) {
    options.shopId=4;
    if (options.shopId>0){
      this.getDetail(options.shopId);
    }else{
      var scene = decodeURIComponent(options.scene);
      console.log("scence=" + scene);
      var prame = scene.split("=");
      console.log("prame=" + prame);
      this.getDetail(prame[1]);
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function (options) {
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    console.log("到底了")
  },
});
