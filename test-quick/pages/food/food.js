Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgUrls: ['https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526189534070&di=e2a970c4663400e618eecba21e0813f3&imgtype=0&src=http%3A%2F%2Fpic65.nipic.com%2Ffile%2F20150423%2F20249953_101217421000_2.jpg', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526189534070&di=e2a970c4663400e618eecba21e0813f3&imgtype=0&src=http%3A%2F%2Fpic65.nipic.com%2Ffile%2F20150423%2F20249953_101217421000_2.jpg', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526189534070&di=e2a970c4663400e618eecba21e0813f3&imgtype=0&src=http%3A%2F%2Fpic65.nipic.com%2Ffile%2F20150423%2F20249953_101217421000_2.jpg',],
  },
  //预览图片
  previewImg: function (e) {
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

  }
})