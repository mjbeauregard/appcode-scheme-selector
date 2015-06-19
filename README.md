# XcodeSchemeSelector plugin for AppCode
A plugin for [AppCode IDE](https://www.jetbrains.com/objc/) that improves on how Xcode schemes are selected.

I created this plugin to workaround the awkward user interface that is built-in to AppCode. XcodeSchemeSelector 
provides an improved user interface for target and device selection in AppCode. It is better than the built-in 
interface because:
* it allows you to choose the target and device independently - in this way it behaves more like Xcode
* removes clutter by filtering out non-executable targets (such as Pods)

## Installation

To install in AppCode:

* Go to `Settings -> Plugins`
* Click `Browse repositories...`
* Search for `XcodeSchemeSelector`
* Install and restart

You can also check it out at the [JetBrains Plugin Repository](https://plugins.jetbrains.com/plugin/7823) where you can 
comment or rate it.

## Usage

When you install XcodeSchemeSelector and restart, you should see two new combo box actions in the `Navigation Toolbar` 
next to AppCode's built-in run configuration selector. The installer doesn't remove the old action, but after playing
around with the new actions you'll probably want to remove the old one to reduce clutter. 

Screenshot of selecting the target with the Core Plot examples project loaded:

![Target Selection](https://github.com/mjbeauregard/appcode-scheme-selector/blob/gh-pages/screenshots/target-selection.png)

and selecting the device:

![Target Selection](https://github.com/mjbeauregard/appcode-scheme-selector/blob/gh-pages/screenshots/device-selection.png)

### License

XcodeSchemeSelector plugin is open-sourced software licensed under the [MIT license](http://opensource.org/licenses/MIT).

