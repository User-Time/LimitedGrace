# LimitedGrace
![Minecraft](https://img.shields.io/badge/Minecraft-1.21+-brightgreen)
![Platform](https://img.shields.io/badge/Platform-Paper%20%7C%20Spigot-orange)
![Java](https://img.shields.io/badge/Java-17+-blue)
![License](https://img.shields.io/github/license/User-Time/LimitedGrace)
![Release](https://img.shields.io/github/v/release/User-Time/LimitedGrace)
![Downloads](https://img.shields.io/github/downloads/User-Time/LimitedGrace/total)

A lightweight Minecraft plugin that provides **limited death protection** for your players! ✨

Players keep their inventory and experience for a configurable number of deaths, making it perfect for survival servers that want to reduce early-game frustration while maintaining long-term balance. 🎮

---

## ✨ Features

* **Dual Protection Layers**: Supports both "Newbie Protection" (default) and "Extra Protection" (manually added).
* **Total Inventory Retention**: Items are **not dropped** during protected deaths.
* **XP Preservation**: Experience **is not lost** when a player is under protection.
* **Smart Warnings**: Automatic notifications when protection charges are running low.
* **Admin Control**: Fully manage death counts and protection levels via commands.

---

## 💻 Commands & Permissions

| Command | Description | Permission | Default |
| :--- | :--- | :--- | :--- |
| `/lg get` | Check your remaining protection | `limitedgrace.get` | Everyone |
| `/lg get <player>` | Check another player's protection | `limitedgrace.get.it` | OP |
| `/lg getDeaths [player]` | View current death counts | `limitedgrace.get` | Everyone |
| `/lg add <count>` | Add extra protection charges | `limitedgrace.add` | OP |
| `/lg add <player> <count>` | Add extra protection charges | `limitedgrace.add.it` | OP |
| `/lg set <player> <count>` | Set total protection charges | `limitedgrace.set` | OP |
| `/lg setDeath <player> <count>` | Modify a player's death count | `limitedgrace.setdeath` | OP |
| `/lg reload` | Reload the plugin configuration | `limitedgrace.reload` | OP |

Alias:
  - /lg

---
## ⚙️ Configuration

The `config.yml` allows you to customize protection limits and localized messages.

```yaml
# Number of protections
death-protections-number: 10
default-added-protections-number: 0

# Does the game warn players when they have only a few protection opportunities left?
protect-warn:
  - 3
  - 1

# Message
protect-message: "§a玩家 §f{0} §a具有 §e{1}次 §a死亡保护§f,\n§a其中包含 §e{2}次 §a新人保护§f,§a以及 §e{3}次 §a的额外死亡保护§f."
protect-warn-message: "§a你只剩 §e{0}次 §a死亡保护了§f, \n§a其中新人保护仅剩 §e{1}次§f, §a额外死亡保护仅剩 §e{2}次§f."
death-message: "§a玩家 §f{0} §a已死亡：§e{1}次"
not-permission-message: "§c你没有使用该命令的权限！"
reload-message: "§a配置已重新加载!"
set-player-death-message: "§a已将玩家§f %s §a的死亡次数修改为:§e %d"
set-player-added-permission-message: "§a已将玩家§f %s §a的额外死亡保护次数修改为:§e %d次"
player-404-message: "§c玩家不存在或不在线"
value-err-message: "§c数值不合法"
set-added-protect-message: "§a已将玩家 §f{0} §a的额外死亡保护设为：§e{1}次"
```

---

## How It Works

Each player has a limited number of **protected deaths**.

During a protected death:
- Items are **not dropped**
- Experience **is not lost**

After the protection runs out, players will die normally.

---

## Requirements

- Minecraft **1.21+**
- Spigot / Paper server

---

## Installation

1. Download the plugin `.jar`
2. Place it into the `plugins` folder
3. Start or reload the server

---

## Configuration

Example configuration:
