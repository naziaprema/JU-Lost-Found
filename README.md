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
| `src/lost/found/login.java` | Login |
| `src/lost/found/dashboard.java` | Dashboard |
| `src/lost/found/reportForm.java` | Lost/found report form |
| `src/lost/found/browseReports.java` | Browse saved reports |
| `src/lost/found/createAccount.java` | Create account |
| `src/lost/found/accountData.java` | Account details and logout |
| `src/lost/found/matchingReports.java` | Compare lost/found reports |
| `src/lost/found/messages.java` | Claims and two-way text messaging |

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

Registration and login use local account files. Student ID and email must be unique (case insensitive). Passwords are 8–128 characters and stored as salted PBKDF2-HMAC-SHA256 hashes (600,000 iterations), never plaintext. Log in with email or student ID. Account Data shows the current account and provides logout.

## Local data

When run from NetBeans, reports are saved under the project's `data/reports/` folder
and photos under `data/images/`. Files remain there after the app closes.
The data directory is relative to the working directory when launched elsewhere.
The optional JVM property `-Dcampusfind.data.dir=PATH` selects another directory.

Reports, user-uploaded photos, generated build files, and machine-specific settings
are excluded from Git. The source includes only the images needed by the GUI.


## Matching

Choose an active saved report on Matching Reports. Suggestions compare only opposite-type active reports; a found date cannot precede the lost date. Shared item-name words are required, excluding common colors/materials. Synonyms include airpod/earbud/earphone, phone/mobile, wallet/purse, and backpack/bag.

The 100-point score uses normalized word-set Jaccard similarity: name 50, description 20, same category 15 (excluding Other), same location 10, and date proximity up to 5. Suggestions need at least 40 points and are sorted highest first. Select a result for its details and component scores. These are text similarity scores, not ownership probabilities. Unknown synonyms, spelling errors, missing details and different dates/locations can reduce or prevent a match; photos are not analyzed.

Account files are stored in data/users/ alongside reports and images. This is local registration on this computer, with no email verification or cross-computer synchronization. The entire data directory is excluded from Git.

## Feature checks

After building, compile test/lost/found/FeatureChecks.java with build/classes on the classpath, then run lost.found.FeatureChecks with build/classes and build/test-classes on the classpath. The checks use isolated temporary data under build/, never real accounts.

## Claims and messages

1. Sign in as the finder and submit a Found report. New reports save the signed-in author's ID and name.
2. Sign in as another account, open Browse Reports, select that Found report, and click **Claim / Contact Finder**. The same button is available for found results in Matching Reports.
3. A conversation opens with the finder. Type a message and press **Send**.
4. The finder opens **Messages / Claims** from the dashboard or Account Data (or **Messages** in Browse), selects the claim, and replies.
5. Conversations refresh every three seconds while open. **Refresh** also checks immediately. Replies and drafts are kept separate; sending supports up to 2,000 characters.

A claim starts a discussion; it does not automatically prove ownership, close the report, or award the item. Each claimant has a separate conversation with the finder. Repeated claims by the same account reopen the existing conversation. The app checks participants before reading or sending messages. Users cannot claim their own reports or start new claims on lost/closed reports.

Claims are UTF-8 text files in data/claims/<conversation-id>.txt. Individual messages are UTF-8 text files in data/claims/<conversation-id>/messages/<message-id>.txt. Atomic file writes and write locks prevent incomplete files and overwritten simultaneous messages. The entire data directory, including messages and accounts, remains excluded from Git.

This is local file-based messaging: accounts must use the same data folder (for example, two accounts on the same computer). Separate computers with independent folders do not receive one another's messages. There is no server, internet delivery, email or SMS. Message files are ordinary text accessible to anyone with operating-system access to that folder.

Older reports without a saved author ID remain browseable and matchable. They cannot safely be assigned to a finder; the finder must sign in and submit a new report to enable claims. Report submission now requires login.

Claim checks: compile test/lost/found/ClaimChecks.java against build/classes and run lost.found.ClaimChecks with build/classes and build/test-classes on the classpath. Tests create isolated temporary accounts/reports/messages under build/, including concurrent sends, participant checks, Unicode round trips and the inbox Send/refresh behavior.

## Finish a return

After handing the item back, the finder signs in, opens Browse Reports, selects their own active Found report, and clicks **Mark as Returned** in the left panel. The button is disabled for other accounts, lost reports, and items already returned. The selected report stays selected after refresh so its **Status: Returned** is visible in the details.

The report text file keeps its original details and photo reference, and saves status=Returned, returnedAt (UTC timestamp), and returnedBy (finder account ID). Return updates are atomic and share the claim-creation lock, so a new claim cannot slip in after an item has been returned. Repeating the update does not change its original return time.

Returned reports stay in Browse Reports for reference. They are excluded from new matches and new claims. Existing claim conversations remain readable and can still receive replies. Use Refresh in an already-open Browse/Matching page to load the latest report status.

ReturnChecks covers the complete two-account journey: report, match, claim, two-way messages, finder-only return, persisted status, exclusion from matches/new claims, and continued conversation access. It also checks the GUI button and concurrent claim/return handling using temporary data under build/. Run test/lost/found/ReturnChecks.java in the same way as the other feature checks.

## Contact a selected match

On Matching Reports, choose a source report and a suggested result, then click **Message Selected Match**. This opens the specific conversation immediately instead of the general inbox. A lost-item owner can contact the finder; a finder selecting their own Found report can contact the matched Lost report's author. Both directions reuse the same conversation. No message is sent until you type one and press Send. Older lost reports without an account link cannot receive direct contact from a finder. Returned or otherwise inactive report pairs are rejected after reloading their latest saved state.

MatchContactChecks verifies both contact directions, conversation reuse, invalid/stale pairs, and that the GUI button opens the selected conversation.