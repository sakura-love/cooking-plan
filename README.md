# 料理计划

<p align="center">
  <img src="./docs/readme-hero.png" alt="料理计划：把做饭变成可执行任务" width="100%" />
</p>

<p align="center">
  <strong>把菜谱拆成任务流的做饭助手</strong> · <strong>200+ 本地菜谱</strong> · <strong>离线优先</strong> · <strong>步骤打勾</strong> · <strong>烹饪计时</strong> · <strong>一周菜单</strong> · <strong>自动买菜清单</strong>
</p>

<p align="center">
  <a href="./releases/料理计划-v1.2.8.apk">下载 APK</a> · <a href="./CHANGELOG.md">更新日志</a> · <a href="./LICENSE">许可证</a>
</p>

> 不是“收藏菜谱”，而是把做饭这件事真正执行完。

## 一句话介绍

`料理计划` 是一款面向真实下厨场景的 Android 做饭助手。它把一道菜拆成可以执行的任务流，让你从“翻菜谱”切换到“按步骤完成”。

## 它解决的问题

- 步骤看着看着就乱了
- 煮着煮着忘了时间
- 买菜总少买一样、又多买一样
- 一周吃什么要临时想
- 厨房新手需要更明确的执行指引

## 核心亮点

<table>
  <tr>
    <td valign="top" width="50%">
      <strong>任务式菜谱</strong><br />
      菜谱不只是文本，而是可勾选、可推进的步骤流。做饭时像执行清单一样一路往下做。
    </td>
    <td valign="top" width="50%">
      <strong>步骤内置计时器</strong><br />
      焯水、腌制、炖煮、蒸烤这类需要时间的步骤，可以直接在流程里启动计时。
    </td>
  </tr>
  <tr>
    <td valign="top" width="50%">
      <strong>一周三餐计划</strong><br />
      可提前安排早餐、午餐、晚餐，同一顿饭也能放入多道菜，适合整周统筹。
    </td>
    <td valign="top" width="50%">
      <strong>自动买菜清单</strong><br />
      根据计划汇总当天或单餐所需食材，买菜时逐项勾选，减少遗漏和重复。
    </td>
  </tr>
  <tr>
    <td valign="top" width="50%">
      <strong>200+ 本地菜谱</strong><br />
      内置家常菜、主食、汤、凉菜、小吃和甜品，没接入 AI 也能直接离线使用。
    </td>
    <td valign="top" width="50%">
      <strong>可选 AI 生成</strong><br />
      可配置 DeepSeek、智谱、OpenAI 或兼容平台，按自己的口味快速生成新菜谱。
    </td>
  </tr>
  <tr>
    <td valign="top" width="50%">
      <strong>收藏常做菜</strong><br />
      常做菜可以单独收藏，减少重复搜索，常用菜单更容易回到手边。
    </td>
    <td valign="top" width="50%">
      <strong>新手引导 + 深色模式</strong><br />
      提供厨房名词和基础操作说明，并支持跟随系统、浅色和深色模式。
    </td>
  </tr>
</table>

## 界面展示

### 主流程

<table>
  <tr>
    <td width="50%">
      <img src="./docs/screenshots/home.png" alt="首页目录与搜索" width="100%" />
      <p align="center"><strong>首页找菜</strong></p>
    </td>
    <td width="50%">
      <img src="./docs/screenshots/recipe-detail.png" alt="菜谱详情" width="100%" />
      <p align="center"><strong>菜谱详情</strong></p>
    </td>
  </tr>
  <tr>
    <td width="50%">
      <img src="./docs/screenshots/recipe-steps.png" alt="菜谱步骤" width="100%" />
      <p align="center"><strong>步骤执行</strong></p>
    </td>
    <td width="50%">
      <img src="./docs/screenshots/recipe-timer.png" alt="步骤计时器" width="100%" />
      <p align="center"><strong>步骤计时</strong></p>
    </td>
  </tr>
</table>

### 计划与采购

<table>
  <tr>
    <td width="50%">
      <img src="./docs/screenshots/meal-plan.png" alt="每周三餐计划" width="100%" />
      <p align="center"><strong>一周计划</strong></p>
    </td>
    <td width="50%">
      <img src="./docs/screenshots/daily-shopping-list.png" alt="买菜清单" width="100%" />
      <p align="center"><strong>当天买菜清单</strong></p>
    </td>
  </tr>
  <tr>
    <td width="50%">
      <img src="./docs/screenshots/favorites.png" alt="收藏页面" width="100%" />
      <p align="center"><strong>收藏常做菜</strong></p>
    </td>
    <td width="50%">
      <img src="./docs/screenshots/settings.png" alt="设置与 AI 配置" width="100%" />
      <p align="center"><strong>设置与 AI 配置</strong></p>
    </td>
  </tr>
</table>

## 适合谁

- 经常自己做饭的人
- 想提前规划一周菜单的人
- 想顺手整理买菜清单的人
- 做饭时容易忘步骤、忘计时的人
- 厨房新手，需要更清晰执行流程的人
- 喜欢离线、本地工具的人

## 安装与使用

### 直接安装 APK

发布包位于 [releases](./releases/) 目录。

当前版本：

- `releases/料理计划-v1.2.8.apk`

如果手机里已经安装过正式版，请继续安装 `release` APK 覆盖升级，不要用 `debug` 包直接覆盖。

### 本地构建

```bash
./gradlew assembleDebug
```

正式签名构建：

```bash
./gradlew assembleRelease
```

## 技术栈

- Kotlin
- Jetpack Compose
- Material 3
- Room
- Hilt
- Navigation Compose
- kotlinx-serialization
- OkHttp

## 项目结构

```text
app/src/main/java/com/cooking/plan/
├─ data/
│  ├─ ai/          # AI 菜谱生成
│  ├─ local/       # Room 数据、默认菜谱
│  ├─ settings/    # 本地设置
│  └─ timer/       # 烹饪计时与前台服务
├─ di/             # 依赖注入
└─ ui/
   ├─ home/        # 首页与目录浏览
   ├─ plan/        # 每周计划与单餐详情
   ├─ recipe/      # 菜谱编辑 / 详情
   ├─ shopping/    # 采购清单
   ├─ favorites/   # 收藏
   ├─ guide/       # 新手指引
   └─ settings/    # 设置
```

## 路线图

- [x] 任务式菜谱
- [x] 做饭计时器
- [x] 每周三餐计划
- [x] 当日买菜清单
- [x] 200+ 默认离线菜谱
- [x] 首页分类目录
- [x] 实机截图补全 README
- [ ] 更完整的菜谱封面展示
- [ ] 更细的口味 / 难度 / 场景筛选
- [ ] 更完整的菜谱导入导出
- [ ] 更细的多菜协同做饭流程

## 更新日志

本次重点更新见 [CHANGELOG.md](./CHANGELOG.md)。

## 许可证

本项目采用非商业源码授权协议，允许学习、研究、修改和非商业分发，禁止未经授权的商业使用。

注意：由于协议禁止商业使用，它属于非商业 source-available 授权，不是 OSI 认证的开源协议。如需商业使用，需要另行取得授权。

## 结束语

如果你也觉得做饭软件应该帮人把饭做出来，而不是只让人收藏菜谱，这个项目应该会对你有用。
