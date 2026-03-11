# LimitedGrace
![Minecraft](https://img.shields.io/badge/Minecraft-1.21+-brightgreen)
![Platform](https://img.shields.io/badge/Platform-Paper%20%7C%20Spigot-orange)
![Java](https://img.shields.io/badge/Java-17+-blue)
![License](https://img.shields.io/github/license/User-Time/LimitedGrace)
![Release](https://img.shields.io/github/v/release/User-Time/LimitedGrace)
![Downloads](https://img.shields.io/github/downloads/User-Time/LimitedGrace/total)

A lightweight Minecraft plugin that provides **limited death protection**.

Players keep their inventory for the first configurable number of deaths.

Perfect for survival servers that want to reduce early frustration while maintaining game balance.

---

## Features

- Configurable number of protected deaths
- Players keep **inventory and experience** during protected deaths
- Automatic **remaining protection warnings**
- Admin commands to **view or modify player death counts**
- Lightweight and simple configuration

---

## Commands

| Command | Description | Permission |
|-------|-------------|------------|
| `/lg get` | Check your remaining protection | `limitedgrace.get` |
| `/lg get <player>` | Check another player's remaining protection | `limitedgrace.get.it` |
| `/lg getDeaths [player]` | View death counts | `limitedgrace.get` |
| `/lg set <player> <count>` | Set a player's death count | `limitedgrace.set` |
| `/lg reload` | Reload the configuration | `limitedgrace.reload` |

Alias:
  - /lg

---

## Permissions

| Permission | Description | Default |
|-----------|------------|--------|
| `limitedgrace.admin` | Full admin permission | OP |
| `limitedgrace.reload` | Reload config | OP |
| `limitedgrace.set` | Modify player death counts | OP |
| `limitedgrace.get` | View own protection | Everyone |
| `limitedgrace.get.it` | View other players' protection | OP |

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

```yaml
# Number of protections
protections-number: 10
# Does the game warn players when they have only a few protection opportunities left?
protect-warn:
  - 3
  - 1

# Message
protect-message: "§a具有 §f{1} §a次死亡保护"
protect-warn-message: "§a你只剩 §e{0} 次§a死亡保护了"
death-message: "§c已死亡：§f{0} §a次"
not-permission-message: "§c你没有使用该命令的权限！"
reload-message: "§a配置已重新加载"
set-player-death-message: "§a已将玩家§e %s §a的死亡次数修改为 %d"
player-404-message: "§c玩家不存在或不在线"
value-err-message: "§c数值不合法"
```
