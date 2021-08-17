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

A docker image helping you to fetch your v2ex favorite topics. Download them as a json file and save to your local disk.

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
