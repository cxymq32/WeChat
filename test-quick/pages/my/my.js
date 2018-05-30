//index.js
//获取应用实例
var app = getApp();
Page({
  data: {
    userInfo:{
      avatarUrl:'',
      nickName:'立即授权'
    },
  },
  bindViewTap: function () {
    var that = this;
    wx.openSetting({
      success: function (res) {        // 这里重新调用代码，比如这里的重新显示头像昵称
        console.log(res)
        res.authSetting = {
          "scope.userInfo": true,
        }
      },
      complete: function () {
        wx.getUserInfo({
          success: function (res) {
            that.setData({
              userInfo: res.userInfo
            })
          }
        })
      }
    })
  },  
  onShow: function () {
    console.log("onShow")
    var that = this;
    wx.getUserInfo({
      success: function (res) {
        console.log(res)
        that.setData({
          userInfo: res.userInfo
        })
        console.log(wx.getStorageSync("openid"))
        wx.request({
          url: app.data.servsers +"/markNewUser",
          data: {
            userInfo: JSON.stringify(res.userInfo),
            openid:wx.getStorageSync("openid")
          },
          method: 'POST',
          header: {
            'content-type': 'application/x-www-form-urlencoded;charset=utf-8'
          },
          success: function (res) {
            console.log(res.data)
          }
        })
      }
    })
  },
  onLoad: function () {
  }
})
