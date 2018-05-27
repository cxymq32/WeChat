Page({

  showImage: function () {
    wx.previewImage({
      current: '', // 当前显示图片的http链接
      urls: ['https://test-1256652767.cos.ap-chengdu.myqcloud.com/2pcode.jpg'] // 需要预览的图片http链接列表
    })
  },  
})