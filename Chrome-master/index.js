const puppeteer = require('puppeteer');

(async () => {
    const browser = await puppeteer.launch({
        executablePath: './Chromium/chrome.exe',//chrome浏览器地址
        headless:true, //是否隐藏浏览器
        timeout: 15000, //设置超时时间,
        ignoreHTTPSErrors: true // 如果是访问https页面 此属性会忽略https错误,
    })
    const page = await browser.newPage()
    await page.goto('https://baidu.com',{ waitUntil: 'load' })
    await page.type('#kw', 'www.scscms.com', {delay: 200})
    page.click('#su')
    await page.waitFor(1000)
    const targetLink = await page.evaluate(() => {
        return document.querySelector('.result a').href
    })
    await page.goto(targetLink,{ waitUntil: 'load' })
    await page.screenshot({path: 'scscms.png'})
    console.log('成功保存图片！')
    browser.close()
})()
