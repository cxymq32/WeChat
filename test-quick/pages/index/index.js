var app = getApp();
var page = 1;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    inputShowed: false,
    inputVal: "",
    shopList: "",
    load:0
  },
  //获取首页数据
  getIndexData:function(){
    var that = this;
    wx.request({
      url: app.data.servsers + '/getShopByPage', //仅为示例，并非真实的接口地址
      data: {
      },
      method: 'get',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data)
        that.setData({
          shopList: res.data
        });
      },
      fail: function (res) {
        console.log(res)
        console.log(app.data.servsers + '/getShopByPage')
      }
    })
  },
  clearInput: function () {
    page = 1;
    this.setData({
      inputVal: "", 
        shopList: "",
        load: 0
    });
    this.getIndexData();
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
    page=1;
    this.setData({
      shopList: "",
      load:0
    })
    this.getIndexData();
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
    console.log(this.data.load + '||' + page)
    var that = this;
    if (this.data.load == 0) {
      this.setData({
        load: 1
      })
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
          }else{
            that.setData({
              shopList: that.data.shopList.concat(res.data),
              load : 0
            });
          }
        }
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})