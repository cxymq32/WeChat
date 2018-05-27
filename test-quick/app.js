//app.js
App({
  data: {
    // servsers: "https://127.0.0.1:8443/test-json/wx",
    servsers: "https://123.207.175.166/test-json/wx",
    // servsers: "https://www.coconet.net.cn:8443/test-json/wx"
  },
  onLaunch: function () {
    var opid = wx.getStorageSync('openid');
    console.log(opid);    
    if (!opid){
      this.getUserInfo();
    }
  },
  
  getUserInfo:function (cb) {
    var that = this;
      //调用登录接口
      wx.login({
        success: function (res) {
          console.log(res)
          wx.request({
            //后台接口地址
            url: 'https://api.weixin.qq.com/sns/jscode2session',
            data: {
              appid: 'wx7255a01c5dfe1f7c',
              secret: 'e035d9830443adaa943e7f6415b20c21',
              grant_type: 'authorization_code',
              js_code: res.code,
            },
            method: 'GET',
            header: { 'content-type': 'application/json' },
            success: function (res) {
              console.log(res)
              wx.setStorageSync('openid', res.data.openid);
            }
          })
          wx.getUserInfo({
            success: function (res) {
              wx.setStorageSync('userInfo', res.userInfo);
            }
          })
        },

      })
  }
})
