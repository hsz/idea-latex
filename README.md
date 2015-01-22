idea-latex [![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=V6QCW4DR2XWY4) <a href="http://blockchain.info/address/1BUbqKrUBmGGSnMybzGCsJyAWJbh4CcwE1"><img src="https://www.gnu.org/software/octave/images/donate-bitcoin.png" width="100" height="21"/></a>
==========

Introduction
------------

**LaTeX** is a plugin supporting LaTeX in your project. It supports following JetBrains IDEs:

- Android Studio
- AppCode
- IntelliJ IDEA
- PhpStorm
- PyCharm
- RubyMine
- WebStorm
- 0xDBE

*Compiled with Java 1.6*


Features
--------

- Syntax highlighting
- Editor toolbar actions with shortcuts

*Feature requests:*

- *suggesting functions' names*
- *obtaining installed packages*
- *generating PDF files*
- *preview generated PDF files*
- *and more...*


Installation
------------

- Using IDE built-in plugin system:
  - <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "LaTeX"</kbd> > <kbd>Install Plugin</kbd>
- Manually:
  - Download the [latest release][latest-release] and install it manually using <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Install plugin from disk...</kbd>
  
Restart IDE.


Usage
-----

### Available shortcuts:

| Action name      | Output                                                                   | Shortcut                                         |
| ---------------- | ------------------------------------------------------------------------ | ------------------------------------------------ |
| *Bold*           | <pre lang="latex">\textbf($SELECTION$)</pre>                             | <kbd>Ctrl</kbd> + <kbd>L</kbd>, <kbd>**B**</kbd> |
| *Italic*         | <pre lang="latex">\textit($SELECTION$)</pre>                             | <kbd>Ctrl</kbd> + <kbd>L</kbd>, <kbd>**I**</kbd> |
| *Underline*      | <pre lang="latex">\underline($SELECTION$)</pre>                          | <kbd>Ctrl</kbd> + <kbd>L</kbd>, <kbd>**U**</kbd> |
| *Align left*     | <pre lang="latex">\begin{flushleft}$SELECTION$\end{flushleft}</pre>      | <kbd>Ctrl</kbd> + <kbd>L</kbd>, <kbd>**L**</kbd> |
| *Align center*   | <pre lang="latex">\begin{center}$SELECTION$\end{center}</pre>            | <kbd>Ctrl</kbd> + <kbd>L</kbd>, <kbd>**C**</kbd> |
| *Align right*    | <pre lang="latex">\begin{flushright}$SELECTION$\end{flushright}</pre>    | <kbd>Ctrl</kbd> + <kbd>L</kbd>, <kbd>**R**</kbd> |


Changelog
---------

Version 0.2

- Toolbar actions: bold, italic, underline, align left, align center, align right

Version 0.1.1

- Fixed syntax highlighting (better BNF and Flex rules)

Version 0.1

- Basic syntax highlighting


Developed By
------------

[**@hsz** Jakub Chrzanowski][hsz]


**Contributors**

- *you can be first on this list*


License
-------

Copyright (c) 2015 hsz Jakub Chrzanowski. See the [LICENSE](./LICENSE) file for license rights and limitations (MIT).

    
[hsz]:                    http://hsz.mobi
[latest-release]:         https://github.com/hsz/idea-latex/releases/latest
