import puppeteer from 'puppeteer'

//自动首屏测试
async function sendPuppeteer(url) {
    const browser = await puppeteer.launch({
        executablePath:'C:/Users/10057959/AppData/Local/Google/Chrome/Application/chrome.exe',//chrome浏览器地址
        timeout: 15000, //设置超时时间,
        ignoreHTTPSErrors: true // 如果是访问https页面 此属性会忽略https错误,
    });
    const context = await browser.createIncognitoBrowserContext();
    const page = await context.newPage();
    const screen = {
        "width": 800,
        "height": 600,
        "deviceScaleFactor": 1,
        "isMobile": false,
        "hasTouch": false,
        "isLandscape": false
    }
    await page.setCacheEnabled(true) //禁用缓存
    await page.setViewport(screen) //设置屏幕
    let err = null;
    await page.goto(url, { waitUntil: 'load' }).then(async ()=>{
        await page.evaluate(() => {
            let w = window;
            w.localStorage.clear() //清空本地存储
            w.monitor.init({uin:'puppeteer'})
            w.monitor.performance()
        })
        let file = `${url.match(/.+\/([^:?]+)/)[1]}${Date.now()}.png`
        let path = `${config.upPath}/${file}`
        await page.screenshot({path});
        let  states = fs.statSync(path);
        //await saveUpFile([1,file,path,'image/png',states.size,!1,new Date().toLocaleString()]);
    }).catch(async e=>{
        err = e.message
        //await sendEmail(config.emailServer.auth.email, url+'网站异常', `puppeteer检测到此网站异常，错误信息：${err}`)
        //发送邮件通知
    })
    await browser.close();
    return err;
}