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
    day:"今天",
    days: days,
    hour:h,
    hours: hours,
    minute:m,
    minutes: minutes,
    people:1,
    phone:"",
    remark:"",
    shopId:0,
    shopName:"",
    adress:"",
    shopPhone:"",
    longitude: "",
    latitude: "",
    sale: "",
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
      showtime: "block"
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
    this.setData({ showtime: "none" })
    
   },
  comfirmtime: function () {
    this.setData({ showtime: "none" })
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
    console.log(e.currentTarget.dataset.index);
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
      phoneNumber: this.data.shopPhone
    })
  },
  //导航
  goto:function(){
    wx.openLocation({
      latitude: this.data.latitude, 
      longitude: this.data.longitude, 
      name: this.data.shopName, 
      scale: 29
    })
  },
  //预订
  comfirorder:function(e){
    if (!this.data.phone){
      wx.showToast({
       title: '请填写预留手机号码',
       icon:"none",
       duration: 2000
      })
      return false;
    }
    var date = this.data.day + this.data.hour + "时" + this.data.minute;
    var people = this.data.people;
    var phone = this.data.phone;
    var remark = this.data.remark;
    var shopId = this.data.shopId;
    wx.showModal({
      title: this.data.phone,
      content: '您' + this.data.people + '人将于' + this.data.day + this.data.hour + "时" + this.data.minute + "分到店，备注:" + remark,
      success: function (res) {
        if (res.confirm) {
          wx.showLoading({
            title: '预约中',
          })
          wx.request({
            url: getApp().data.servsers+'/comfirorder', //仅为示例，并非真实的接口地址
            data: {
              arriveTime: date, phone: phone, people: people, remark: remark,shopId:shopId
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
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: getApp().data.servsers +'/getByPage', //仅为示例，并非真实的接口地址
      data: {
        id: options.shopId,
      },
      method: 'get',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data[0])
        that.data.imgUrls.push(res.data[0].mainImage)
        that.data.imgUrls.push(res.data[0].image1)
        that.data.imgUrls.push(res.data[0].image2)
        that.data.imgUrls.push(res.data[0].image3)
        that.setData({
          shopName: res.data[0].shopName,
          adress: res.data[0].adress,
          shopPhone: res.data[0].phone,
          latitude: res.data[0].latitude,
          longitude: res.data[0].longitude,
          sale: res.data[0].sale,
          imgUrls: that.data.imgUrls,
        });
        wx.setNavigationBarTitle({
          title: res.data[0].shopName
        })
      }
    })
    
    that.setData({
      shopId: options.shopId
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

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
