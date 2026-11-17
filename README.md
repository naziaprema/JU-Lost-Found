# CampusFind — JU Lost & Found

A simple Java Swing desktop app for reporting and browsing lost and found items on campus.
The app uses local text files and image folders, with no database or server.

## Open and run

1. Install JDK 25 and Apache NetBeans.
2. Open this folder as a project in NetBeans.
3. Select **Clean and Build**, then press **F6** to start from the login screen.
4. To preview a page directly, open its Java file and press **Shift+F6**.

The project currently targets Java 24 bytecode and builds with JDK 25.
For command-line builds, run `ant jar`, then `java -jar dist/Lost_Found.jar`.

## Edit the GUI

Open a form's Java file and select **Design**:

| File | Screen |
| --- | --- |
| `src/lost/found/login.java` | Login preview |
| `src/lost/found/dashboard.java` | Dashboard |
| `src/lost/found/reportForm.java` | Lost/found report form |
| `src/lost/found/browseReports.java` | Browse saved reports |

Each form has a matching `.form` file. Keep both files together.
Use Design view to change controls, labels, dropdown models, and layout.
Press **Ctrl+S**, close the old running window, and run again.
Do not edit the guarded `initComponents()` code directly in NetBeans.

Compile-on-save is disabled locally to avoid incomplete builds; normal Run compiles saved source.
On a new machine, disable **Project Properties → Build → Compiling → Compile on Save**
if incremental builds fail to include button-listener classes.

## Implemented

- Simple dashboard and sidebar navigation.
- Shared lost/found form with required-field and date validation.
- Dropdown containing 72 campus locations.
- Optional photo selection and copying into app storage.
- UTF-8 text-file report storage.
- Browsing, text search, Lost/Found filtering, sorting, report details, and photo previews.
- Refresh and back navigation.

Login is currently a preview: it checks for nonempty fields but does not authenticate accounts.
Matching, account management, and registration are still to be implemented.

## Local data

When run from NetBeans, reports are saved under the project's `data/reports/` folder
and photos under `data/images/`. Files remain there after the app closes.
The data directory is relative to the working directory when launched elsewhere.
The optional JVM property `-Dcampusfind.data.dir=PATH` selects another directory.

Reports, user-uploaded photos, generated build files, and machine-specific settings
are excluded from Git. The source includes only the images needed by the GUI.
