# GetMyV2exTopicsList
get my v2ex topics list. 快速获取 v2ex 上我收藏的帖子列表

---

# 2021-08-04 16:32:15 update

导出的每个帖子信息中新增创建者

![20210804163000.png](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/20210804163000.png)

# 我收藏的帖子状况

595个帖子

![d31db622b9ac789c9944a283884fc37.png](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/d31db622b9ac789c9944a283884fc37.png)

30 页数据

![35aee511c6072a1762273fd803c9d9f.png](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/35aee511c6072a1762273fd803c9d9f.png)

但是这个页面没有搜索功能，有时候想找一个印象中曾经收藏过的帖子，翻遍了好几页也没找到，甚至找了几页又找了回来，担心看丢。

# 效果预览

导出后在终端当前文件夹下生成 `myv2extopicslist.json` 文件。下面是一个简单的预览

![9e1bade85988334b0d283d81de51701.png](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/9e1bade85988334b0d283d81de51701.png)

# 使用方法

## 1. 获取 v 站的 cookies

如下图对应的 v 站的 5 个 cookie

![28e1d381b6afadd54d066b50ca26348.png](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/28e1d381b6afadd54d066b50ca26348.png)

## 2. 下载 jar 包

去该项目的 [release](https://github.com/hellodk34/GetMyV2exTopicsList/releases) 页面下载最新版本的 jar 包到本地

## 3. 本地通过 `java -jar` 运行

确保本机安装有 java 8 以上环境。

打开终端，运行 `java -jar .\getv2exmytopics-0.0.1-SNAPSHOT.jar TOTAL_PAGE_NUMBER "A2=aaa" "V2EX_LANG=zhcn" "PB3_SESSION=bbb" "V2EX_TAB=ccc" "V2EX_REFERRER=ddd"`

将对应的 cookie 值替换成你自己的。

运行截图

![20210726135117.png](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/20210726135117.png)

![20210726135135.png](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/20210726135135.png)

![20210726161316.png](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/20210726161316.png)

---

注意：

- TOTAL_PAGE_NUMBER 打开 https://www.v2ex.com/my/topics 查看收藏的帖子共有多少页。此值需要小于等于你看到的页数。由于没有请求 v 站相应接口获取页数，所以这个值没有最大值校验。但运行时你可以输入 1 2 等只导出第一页或前两页数据
- cookies 需要写成 "COOKIE_NAME=COOKIE_VALUE" 的形式。在 PowerShell 下需要使用双引号包裹 cookie 的键和值
- 由于 v 站现在被墙，所以建议你使用科学上网的路由器运行。或者在终端使用临时的代理
- v 站 COOKIE_VALUE cookie 的值（字符串类型）用双引号包裹了。填入 "COOKIE_NAME=COOKIE_VALUE" 时，需要把 复制过来的 COOKIE_VALUE 两端的双引号去掉
- 遇到 Caused by: java.io.EOFException: SSL peer shut down incorrectly 问题建议优先更换代理节点。比如美国节点更换成新加坡节点
- 如果不具备科学上网路由器也可以在你的海外 VPS 上执行这个任务。使用 `wget` 将 jar 包下载到 vps 上然后执行任务 （也很建议这么做）

Linux/macOS 下终端创建临时代理

```
export https_proxy=http://127.0.0.1:7890

export http_proxy=http://127.0.0.1:7890
```

Windows 下使用 cmd 或者 PowerShell 创建临时代理

```
set http_proxy=http://127.0.0.1:7890

set https_proxy=http://127.0.0.1:7890
```

---

# 后记

昨天下雨在家刚好在 n1 上装了一个 leanote 服务。发现还可以直接将文章一键发布成博文，挺有意思的。

我导出了我近期收藏的两页帖子，托管在 n1 上了。

👉🏻 v2ex 我收藏的帖子列表 https://blog.hellodk.com/blog/post/dk11/v2ex-my-topics

---

# deploy by docker

# Image Description

A docker image helping you to fetch your v2ex favorite topics. Download them as a json file and save it to your local disk.

the output is like this

```
{
    "page1": [
        {
            "creator": "xinJang",
            "createdTime": "2020-05-04 07:49:14",
            "title": "原来移动是 nat1，快去看看你的是不是",
            "seq": 1,
            "url": "https://www.v2ex.com/t/668456#reply39"
        },
        {
            "creator": "azev",
            "createdTime": "2021-08-13 12:47:05",
            "title": "大家用什么网页划词翻译插件？",
            "seq": 2,
            "url": "https://www.v2ex.com/t/795672#reply12"
        },
        {
            "creator": "moxuanyuan",
            "createdTime": "2020-06-05 06:13:09",
            "title": "分享一些我喜欢听中文科技类播客节目",
            "seq": 3,
            "url": "https://www.v2ex.com/t/678954#reply0"
        },
        {
            "creator": "iyg429",
            "createdTime": "2021-08-12 07:15:58",
            "title": "不好意思 请问下有没有男士用的洗面奶",
            "seq": 4,
            "url": "https://www.v2ex.com/t/795353#reply160"
        },
        {
            "creator": "balabalaguguji",
            "createdTime": "2021-08-11 06:51:07",
            "title": "分享下好评如潮的 SVN 视频教程",
            "seq": 5,
            "url": "https://www.v2ex.com/t/795084#reply18"
        },
        {
            "creator": "morizawatt",
            "createdTime": "2021-08-09 03:28:42",
            "title": "一个程序员朋友， 90 后，刚查出慢性肾衰，二期",
            "seq": 6,
            "url": "https://www.v2ex.com/t/794579#reply162"
        },
        {
            "creator": "beginor",
            "createdTime": "2021-08-03 01:29:01",
            "title": "聊聊心目中的完美笔记本",
            "seq": 7,
            "url": "https://www.v2ex.com/t/793294#reply152"
        },
        {
            "creator": "xiaoz",
            "createdTime": "2021-08-07 01:48:48",
            "title": "有适合自建的文档系统推荐吗？",
            "seq": 8,
            "url": "https://www.v2ex.com/t/794225#reply16"
        },
        {
            "creator": "junas7",
            "createdTime": "2021-08-05 05:42:22",
            "title": "迫于伞兵网友太多，求一个没有评论、社交属性的新闻获取途径",
            "seq": 9,
            "url": "https://www.v2ex.com/t/793834#reply51"
        },
        {
            "creator": "goodhellonice",
            "createdTime": "2021-08-03 06:38:36",
            "title": "V 友们平时都喝什么咖啡？有什么无（低）糖速溶咖啡推荐么？",
            "seq": 10,
            "url": "https://www.v2ex.com/t/793386#reply105"
        },
        {
            "creator": "fyxtc",
            "createdTime": "2021-08-03 03:53:54",
            "title": "游戏行业完了？",
            "seq": 11,
            "url": "https://www.v2ex.com/t/793350#reply83"
        },
        {
            "creator": "Kamitora",
            "createdTime": "2018-12-26 13:26:33",
            "title": "打算搭建一个博客，当今应选择哪个博客框架？",
            "seq": 12,
            "url": "https://www.v2ex.com/t/521313#reply199"
        },
        {
            "creator": "seoikei",
            "createdTime": "2021-02-25 02:09:50",
            "title": "各位朋友戴这个手表有出现过过敏吗？（图片有点恶心，慎点）",
            "seq": 13,
            "url": "https://www.v2ex.com/t/756068#reply102"
        },
        {
            "creator": "rockdai",
            "createdTime": "2021-07-12 06:18:25",
            "title": "有没有卖 Apple Watch 表带的店推荐？",
            "seq": 14,
            "url": "https://www.v2ex.com/t/789005#reply19"
        },
        {
            "creator": "leeum",
            "createdTime": "2021-05-19 09:05:41",
            "title": "HUAWEI MateView 用来外接 Mac 如何？",
            "seq": 15,
            "url": "https://www.v2ex.com/t/777946#reply124"
        },
        {
            "creator": "serco",
            "createdTime": "2021-07-28 07:59:52",
            "title": "写个系列文章给 A 股小白看有人有兴趣吗？多年资深亏损经验😂",
            "seq": 16,
            "url": "https://www.v2ex.com/t/792299#reply49"
        },
        {
            "creator": "zictos",
            "createdTime": "2021-07-29 11:14:35",
            "title": "现在还有能发各种宽泛的帖子的高质量的地方吗？",
            "seq": 17,
            "url": "https://www.v2ex.com/t/792537#reply115"
        },
        {
            "creator": "ruiyi1994",
            "createdTime": "2021-07-24 02:17:42",
            "title": "腾讯云 cross the great wall 自用节点被封了",
            "seq": 18,
            "url": "https://www.v2ex.com/t/791468#reply29"
        },
        {
            "creator": "joyhub2140",
            "createdTime": "2021-07-25 13:34:28",
            "title": "一部电影分三个晚上看完。",
            "seq": 19,
            "url": "https://www.v2ex.com/t/791684#reply60"
        },
        {
            "creator": "Pogbag",
            "createdTime": "2021-07-28 04:59:59",
            "title": "家庭 10w 的车有推荐的吗",
            "seq": 20,
            "url": "https://www.v2ex.com/t/792254#reply86"
        }
    ]
}
```

# Usage

## first

docker pull this image

```
docker pull dko0/v2extopicslist:2.0
```

## second

get your v2ex **Cookies**.

Do not know how to get? see also 👉🏻 [GetMyV2exTopicsList](https://github.com/hellodk34/GetMyV2exTopicsList)

## third

create a `config.json` file to save your v2ex Cookies, like this

```
{
  "pageNum": 1,
  "A2": "aaa",
  "V2EX_LANG": "zhcn",
  "PB3_SESSION": "bbb",
  "V2EX_TAB": "ccc",
  "V2EX_REFERRER": "ddd"
}
```

`pageNum` pageNum is the number of pages you want to get. The maximum number cannot be greater than the maximum number of pages of your favorite topics. Such as 1, it will only get first page of your favorite topics.

**Be attention: The key sequence of your json file needs to be the same with the above example json file.**

## fourth

and finally, docker run this image

```
docker run --name v2ex -v /path/to/host-machine-that-contain-above-config-file:/config dko0/v2extopicslist:2.0
```

**ps: please mount `/config` (in container) to your host machine, and make sure the correct `config.json` file is in this folder.**

## fifth

using command `docker cp` to copy the generated json file to host machine

```
docker cp v2ex:/app/myv2extopicslist.json .
```


Congratulations! You could see `myv2extopicslist.json` in current folder.

---

docker image address: https://hub.docker.com/r/dko0/v2extopicslist
