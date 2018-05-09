var app = getApp();
var page = 1;
var load = 0;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    inputShowed: false,
    inputVal: "",
    chooseItem: '',
    shopListTemp: "",
    shopList: "",
    load : 0,
  },
  
  clearInput: function () {
    this.setData({
      inputVal: "",
      shopList:this.data.shopListTemp
    });
  },
  inputTyping: function (e) {
    this.setData({
      inputVal: e.detail.value,
    });
    var that = this;
    wx.request({
      url: getApp().data.servsers + '/getShopByPage', //仅为示例，并非真实的接口地址
      data: {
        shopName: e.detail.value,
      },
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      },
      success: function (res) {
        that.setData({
          shopList: res.data
        });
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: getApp().data.servsers +'/getShopByPage', //仅为示例，并非真实的接口地址
      data: {
      },
      method: 'get',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data)
        that.setData({
          shopList: res.data,
          shopListTemp: res.data,
        });
      }
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
    var that = this;
    if (load == 0) {
      that.setData({
        load: 1
      })
      load = 1;
      page = page + 1;
      wx.request({
        url: getApp().data.servsers + '/getShopByPage', //仅为示例，并非真实的接口地址
        data: {
          page: page
        },
        method: 'get',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          if (res.data.length==0){
            that.setData({
              load: 2
            })
            load = 2;
          }else{
            that.setData({
              shopList: that.data.shopList.concat(res.data),
              load : 0
            });
            load = 0;
          }
        }
      })
    }
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
    var a = wx.getStorageSync('openId');
    console.log(a)
    wx.request({
      url: getApp().data.servsers + '/pay',
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: { 'openid': "otnGe4uyCaDFPj5v5ek-PuasF9gI" },
      success: function (res) {
        var prepay_id = res.data.prepay_id;
        console.log("统一下单返回 prepay_id:" + prepay_id);
        
      }
    })
  }  
})