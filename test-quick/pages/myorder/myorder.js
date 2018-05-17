var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[],
    page :1,
    load:0
  },
  cancel: function (e) {
    var that = this;
    wx.showModal({
      title: '取消',
      content: '您确定要取消预约吗？',
      success: function (res) {
        if (res.confirm) {
          wx.request({
            url: app.data.servsers + '/cancelOrder', //仅为示例，并非真实的接口地址
            data: {
              orderId: e.target.dataset.orderId,
            },
            method: 'get',
            header: {
              'content-type': 'application/json'
            },
            success: function (res) {
              that.setData({
                page: 1,
                list: [],
                load:0
              });
              that.loadData();
            }
          })
        }
      }
    })

  },
  loadData:function(){
    var that = this;
    wx.request({
      url: app.data.servsers + '/getOrderByPage', //仅为示例，并非真实的接口地址
      data: {
        page: that.data.page,
        openId: wx.getStorageSync('openid')
      },
      method: 'get',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data)
        if (res.data.length == 0) {
          that.setData({
            load: 1
          })
        }
        that.setData({
          list: that.data.list.concat(res.data),
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
    this.loadData();
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
    console.log(this.data.load)
    if(this.data.load==0){
      this.setData({
        page: this.data.page + 1
      });
      this.loadData();
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})