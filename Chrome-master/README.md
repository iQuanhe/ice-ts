# Chromium<sup>浏览器</sup>

	众所周知，天朝很难访问google的服务器，但是puppeteer又依赖Chromium/Chrome59+，故在此备份。如果你电脑的Chrome版本在59+就可不需要此文件。

当前版本：`69.0.3465.0（开发者内部版本） （32 位）`

API教程：[https://github.com/zhaoqize/puppeteer-api-zh_CN](https://github.com/zhaoqize/puppeteer-api-zh_CN)
### 安装

特别注意，在此是假设你的电脑没有翻墙。如果有翻墙你也用不着看这个~

- 方法一

执行CMD命令(告诉puppeteer安装时不要下载Chromium)

```
set PUPPETEER_SKIP_CHROMIUM_DOWNLOAD=1
```

执行npm命令

```
npm install
```

- 方法二（忽略下载Chromium）

```
npm install --save puppeteer --ignore-scripts
```
### 解压文件

请把 `Chromium.rar` 解压到当前目录。

###　测试

请查看`index.js`，默认使用`Chromium/chrome.exe`，假如你电脑有`chrome59+`浏览器，也可复制它的地址替换`executablePath`值，如：
```
executablePath: 'c:/Program Files/Google/Chrome/chrome.exe',//chrome浏览器地址
```

然后执行node脚本`node index.js`查看效果。

### 其他替换方案

- puppeteer-cn

