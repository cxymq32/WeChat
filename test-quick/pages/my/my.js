//index.js
//获取应用实例
var app = getApp();
Page({
  data: {
    userInfo:{
      avatarUrl:'http://demo.zhilengzhire.com/weui-SPA-myImg/010.png',
      nickName:'立即授权'
    },
    navi:[
      '../../example/order/order',
      '../../example/collect/collect',
      '../../example/setion/setion',
      '../../example/fdback/fdback',
      
    ],
  },  
  //事件处理函数
  bindViewTap: function () {
   
    var that=this;
    wx.openSetting({
      success: function (res) {        // 这里重新调用代码，比如这里的重新显示头像昵称
        res.authSetting = {
          "scope.userInfo": true,
        }
      },
      complete: function () {
        app.getUserInfo(function (userInfo) {
          //更新数据
          that.setData({
            userInfo: userInfo,
          })
        });
      }
      
    })
  },

  onLoad: function () {
    var that = this;
    that.setData({
      myImg:Data.myImg,
    });
    //调用应用实例的方法获取全局数据
    app.getUserInfo(function(userInfo){
      //更新数据
      that.setData({
        userInfo:userInfo,
      })
    });
    
  }
})
