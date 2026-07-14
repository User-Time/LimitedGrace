# LimitedGrace

![Minecraft](https://img.shields.io/badge/Minecraft-1.21+-brightgreen)
![Platform](https://img.shields.io/badge/Platform-Paper%20%7C%20Spigot-orange)
![Java](https://img.shields.io/badge/Java-21+-blue)
![Version](https://img.shields.io/badge/Version-1.2.0-blueviolet)
![License](https://img.shields.io/github/license/User-Time/LimitedGrace)
![Release](https://img.shields.io/github/v/release/User-Time/LimitedGrace)
![Downloads](https://img.shields.io/github/downloads/User-Time/LimitedGrace/total)

A lightweight Minecraft plugin that provides configurable and flexible **death protection** for players. ✨

LimitedGrace allows players to keep their inventory and experience for a limited number of deaths. It is suitable for survival servers that want to reduce early-game frustration while preserving the long-term challenge of survival gameplay.

Version **1.2.0** introduces protection toggles, global protection control, and an unlimited permission that allows selected players to use death protection without consuming protection charges.

---

## ✨ Features

* **Dual Protection Layers**
  Supports both **Newbie Protection** and **Extra Protection**.

* **Inventory Retention**
  Players do not drop their inventory during protected deaths.

* **Experience Preservation**
  Players do not lose experience while death protection is active.

* **Extra Protection Management**
  Administrators can add or set extra protection charges without modifying the player's actual death count.

* **Personal Protection Toggle**
  Players can use `/lg switch` to enable or disable their own death protection.

* **Other Player Management**
  Administrators can enable or disable death protection for another online player.

* **Global Protection Toggle**
  Administrators can use `/lg switchAll` to enable or disable the plugin's death protection globally.

* **Unlimited Protection Permission**
  Players with `limitedgrace.unlimited` can use death protection without consuming their available protection charges.

* **Smart Warnings**
  Players receive automatic notifications when their remaining protection charges are running low.

* **Detailed Protection Information**
  `/lg get` displays the player's remaining newbie protection and extra protection charges.

* **Administrative Controls**
  Administrators can view and modify death counts, protection charges, player protection states, and the global protection state.

* **Configurable Messages**
  Protection limits, warning thresholds, and plugin messages can be customized through `config.yml`.

* **Java 21 Support**
  Built for Java 21 and modern Minecraft server versions.

---

## 💻 Commands and Permissions

| Command                         | Description                                    | Permission                | Default  |
| :------------------------------ | :--------------------------------------------- | :------------------------ | :------- |
| `/limitedgrace get`                       | View your remaining protection charges         | `limitedgrace.get`        | Everyone |
| `/limitedgrace get <player>`              | View another player's protection charges       | `limitedgrace.get.it`     | OP       |
| `/limitedgrace getDeaths`                 | View your current death count                  | `limitedgrace.get`        | Everyone |
| `/limitedgrace getDeaths <player>`        | View another player's death count              | `limitedgrace.get.it`     | OP       |
| `/limitedgrace add <count>`               | Add extra protection charges to yourself       | `limitedgrace.add`        | OP       |
| `/limitedgrace add <player> <count>`      | Add extra protection charges to another player | `limitedgrace.add.it`     | OP       |
| `/limitedgrace switch`                    | Toggle your own death protection               | `limitedgrace.switch`     | Everyone |
| `/limitedgrace switch <player>`           | Toggle another player's death protection       | `limitedgrace.switch.it`  | OP       |
| `/limitedgrace switchAll`                 | Toggle death protection globally               | `limitedgrace.switch.all` | OP       |
| `/limitedgrace set <player> <count>`      | Set a player's extra protection charges        | `limitedgrace.set`        | OP       |
| `/limitedgrace setDeath <player> <count>` | Set a player's actual death count              | `limitedgrace.setdeath`   | OP       |
| `/limitedgrace reload`                    | Reload the plugin configuration                | `limitedgrace.reload`     | OP       |

### Command Alias

```text
/lg
```

---

## 🔑 Additional Permissions

| Permission                | Description                                                                   | Default  |
| :------------------------ | :---------------------------------------------------------------------------- | :------- |
| `limitedgrace.admin`      | Grants administrative access to LimitedGrace commands                         | OP       |
| `limitedgrace.unlimited`  | Prevents protection charges from being consumed when death protection is used | OP       |
| `limitedgrace.reload`     | Allows the plugin configuration to be reloaded                                | OP       |
| `limitedgrace.set`        | Allows extra protection charges to be modified                                | OP       |
| `limitedgrace.setdeath`   | Allows actual death counts to be modified                                     | OP       |
| `limitedgrace.get`        | Allows players to view their own protection information                       | Everyone |
| `limitedgrace.get.it`     | Allows another player's protection information to be viewed                   | OP       |
| `limitedgrace.add`        | Allows extra protection charges to be added to yourself                       | OP       |
| `limitedgrace.add.it`     | Allows extra protection charges to be added to another player                 | OP       |
| `limitedgrace.switch`     | Allows players to toggle their own death protection                           | Everyone |
| `limitedgrace.switch.it`  | Allows another player's death protection to be toggled                        | OP       |
| `limitedgrace.switch.all` | Allows death protection to be enabled or disabled globally                    | OP       |

---

## ⚙️ Configuration

The `config.yml` file allows protection limits, warning thresholds, and plugin messages to be customized.

```yaml
# Number of newbie protection charges
death-protections-number: 10

# Default number of extra protection charges
default-added-protections-number: 0

# Warn players when their remaining protection reaches these values
protect-warn:
  - 3
  - 1

# Messages
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

After modifying `config.yml`, run:

```text
/lg reload
```

This reloads the configuration and updates the cached configuration values without restarting the server.

---

## 🛡️ How It Works

Each player can receive protection from two independent sources:

1. **Newbie Protection**
   Automatically calculated from the configured protection limit and the player's actual death count.

2. **Extra Protection**
   Additional protection charges granted manually by administrators.

When a protected player dies:

* Inventory items are not dropped.
* Experience is not lost.
* One applicable protection charge is consumed.

Protection is not applied when:

* The player has disabled personal death protection.
* Death protection has been disabled globally.
* The player has no remaining protection charges.

Players with the following permission can use death protection without consuming protection charges:

```text
limitedgrace.unlimited
```

Once all available protection has been exhausted, the player will die normally unless extra protection is granted.

---

## 📋 Requirements

* Minecraft **1.21+**
* Java **21+**
* Paper or Spigot server

---

## 📦 Installation

1. Download the latest LimitedGrace `.jar` file.
2. Place the file in your server's `plugins` directory.
3. Start or restart the server.
4. Edit `plugins/LimitedGrace/config.yml` as needed.
5. Run `/lg reload` after changing the configuration.

---

## 🏷️ Version

Current version:

```text
1.2.0
```
