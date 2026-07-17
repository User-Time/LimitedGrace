# LimitedGrace

![Minecraft](https://img.shields.io/badge/Minecraft-1.19.4%2B-brightgreen)
![Platform](https://img.shields.io/badge/Platform-Paper-orange)
![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Version](https://img.shields.io/badge/Version-1.3.0-blueviolet)
![License](https://img.shields.io/github/license/User-Time/LimitedGrace)
![Release](https://img.shields.io/github/v/release/User-Time/LimitedGrace)
![Downloads](https://img.shields.io/github/downloads/User-Time/LimitedGrace/total)

A lightweight Minecraft plugin that provides configurable and flexible **death protection** for players. ✨

LimitedGrace allows players to keep their inventory and experience for a limited number of deaths. It is suitable for survival servers that want to reduce early-game frustration while preserving the long-term challenge of survival gameplay.

Version **1.3.0** adds an asynchronous GitHub Releases update checker, lowers the minimum requirements to Paper 1.19.4 and Java 17, improves configuration management, and fixes configuration and player protection toggle issues.

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
  
* **GitHub Release Update Checker**
  Asynchronously checks GitHub Releases for new versions when the server starts. Update checking can be disabled in `config.yml`.
  
* **Java 17 Compatibility**
  Supports Java 17 and Paper 1.19.4 or newer.

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
| `limitedgrace.unlimited`  | Prevents protection charges from being consumed when death protection is used | False    |
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
# Global death protection state.
# Use `/lg switchAll` to toggle it.
enabled: true

# Check GitHub Releases for a newer plugin version on startup.
update-check:
  enabled: true

# Number of protection charges.
death-protections-number: 10
default-added-protections-number: 0

# Remaining protection counts at which the player will receive a warning.
protect-warn:
  - 3
  - 1

# Messages support Minecraft color codes using `§`.
# Dynamic values use placeholders such as {0}, {1}, %s, and %d.
help-message:
  - "§6§lLimitedGrace Help"
  - "§e/lg help                         §7- Show plugin help"
  - "§e/lg switch [player]              §7- Toggle protection for yourself or another player"
  - "§e/lg switchAll                    §7- Toggle protection globally"
  - "§e/lg get [player]                 §7- View remaining protection charges"
  - "§e/lg getDeaths [player]           §7- View the death count"
  - "§e/lg set [player] <count>         §7- Set extra protection charges"
  - "§e/lg setDeath [player] <count>    §7- Set the death count"
  - "§e/lg add [player] <count>         §7- Add extra protection charges"
  - "§e/lg reload                       §7- Reload the plugin configuration"

protect-message: "§aPlayer §f{0} §ahas §e{1} §adeath protection charge(s)§f.\n§aThis includes §e{2} §anewbie protection charge(s) §fand §e{3} §aextra protection charge(s)§f."
protect-warn-message: "§aYou have only §e{0} §adeath protection charge(s) remaining§f.\n§aNewbie protection remaining: §e{1}§f; §aextra protection remaining: §e{2}§f."
death-message: "§aPlayer §f{0} §ahas died §e{1} §atime(s)."
not-permission-message: "§cYou do not have permission to use this command!"
reload-message: "§aConfiguration reloaded successfully!"
set-player-death-message: "§aSet player §f%s§a's death count to §e%d§a."
set-player-added-permission-message: "§aSet player §f%s§a's extra protection charges to §e%d§a."
player-404-message: "§cPlayer does not exist or is not online."
value-err-message: "§cInvalid value."
set-added-protect-message: "§aSet player §f{0}§a's extra protection charges to §e{1}§a."
integer-error-message: "§c{0} is not a valid integer."
value-unchanged-message: "§cThe value has not changed."
death-update-error-message: "§cFailed to update the player's death count."
protection-update-error-message: "§cFailed to update the player's extra protection charges."
switch-self-message: "Your death protection state has been changed to {0}."
switch-player-message: "{0}'s death protection state has been changed to {1}."
switch-all-message: "The global death protection state has been changed to {0}."
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

* Minecraft **1.19.4+**
* Java **17+**
* Paper **1.19.4+**

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
1.3.0
```
