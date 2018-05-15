var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  getPhoneNumber: function (e) {
    console.log(e.detail.errMsg)
    console.log(e.detail.iv)
    console.log(e.detail.encryptedData)
  },
  pay: function () {
    if(!wx.getStorageSync("openid")){
      app.getUserInfo();
    }
    console.log(wx.getStorageSync("openid"))
    wx.request({
      //后台接口地址
      url: app.data.servsers +"/pay/getPrepay",
      data: {
        openid: wx.getStorageSync("openid")
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log(res.data)
        wx.requestPayment(
          {
            'timeStamp': res.data.timestamp,
            'nonceStr': res.data.nonceStr,
            'package': res.data.packages,
            'signType': 'MD5',
            'paySign': res.data.finalsign,
            'success': function (res) { console.info(res) },
            'fail': function (res) { console.info(res)},
            'complete': function (res) {
              console.info(res)
             }
        })
      }
    })

  }  
})