## 큐넥트(Qnnect)
### [Google Play Store](https://play.google.com/store/apps/details?id=com.iame.qnnect.android)
## 가족, 친구, 연인과 함께 쓰는 Q&A 다이어리, 큐넥트 하나의 질문 만으로 ‘나’에 대해 알아가고, 나와 가까운 다른 이의 답변을 통해 ‘서로’ 알아갈 수 있는 공유 Q&A 다이어리 서비스, 큐넥트 입니다!
- 1️⃣ - 그룹이 정한 질문 주기(매일, 3일, 1주일..) 에 푸시알림으로 질문 1개를 드립니다.
- 2️⃣ 가족/친구/연인의 답변을 통해 서로에 대해 알아가고, 소통할 수 있어요
- 3️⃣ 답변을 통해 쌓은 포인트로 우리만의 음료 ☕️ 를 만들어보세요!
<p float="left">
 <img src="https://user-images.githubusercontent.com/84956038/174083810-f668b143-a04a-471b-b16d-f4cfc358eace.jpg" width="20%" height="20%%"> 
 <img src="https://user-images.githubusercontent.com/84956038/174083835-4a7861e5-1c6b-4d79-9cd3-c2e54d777a3c.jpg" width="20%" height="20%%">
 <img src="https://user-images.githubusercontent.com/84956038/174083845-faf679d4-517a-4c36-a5d7-2c9db2484acc.jpg" width="20%" height="20%%">
 <img src="https://user-images.githubusercontent.com/84956038/174083844-7ed9f07a-a49a-4242-acd1-6f5eb5a1ccb4.jpg" width="20%" height="20%%">
</p>
<p float="left">
 <img src="https://user-images.githubusercontent.com/84956038/174083850-cd71346d-58c6-48b0-a6ab-ce7993a8d80e.jpg" width="20%" height="20%%">
 <img src="https://user-images.githubusercontent.com/84956038/174083853-b458d1e3-1778-4f12-b6fc-378197489dc4.jpg" width="20%" height="20%%">
 <img src="https://user-images.githubusercontent.com/84956038/174083860-1449e30e-4562-4dc1-9a29-0ca0e3c5e30b.jpg" width="20%" height="20%%">
 <img src="https://user-images.githubusercontent.com/84956038/174083862-df4abf95-1631-4f6b-bd9d-d48870af5b33.jpg" width="20%" height="20%%">
</p>

## stack
- Kotlin
- MVVM(AAC)
- Rxjava + Retrofit2
- lifecycleScope
- Koin
- Firebase Messaging, Firebase DynamicLink
- KakaoLink

## 주차별 진행상황
- 함께 쓰는 Q&A 다이어리 App
- 1st Week
1. 2022.02.12(SAT) -> gradle setting
2. 2022.02.14(MON) -> splash, Login, profile layout
3. 2022.02.16(WEN) -> kakao login, MVVM & Koin(Di) Setting complete
4. 2022.02.17(THU) -> custom check box & edit Profile layout & image upload & edit text len check (use Rxjava)
5. 2022.02.18(FRI) -> Base Fragment modify & Fragment (Home, Bookmark, Store, My page), Splash Activity modify & package name change & not apply dark mode & status bar color change
6. 2022.02.19(SAT) -> Home Frag & My Page Frag (View pager2 & Recycler View Adapter)
7. 2022.02.20(SUN) -> Edit Prfile Activity Update & bottom sheet & next bottom sheet & BookMark Frag

- 2nd Week
1. 2022.02.21(MON) -> add group flow Complete (Change ViewPager2) & Profile image upload flow complete
2. 2022.02.22(TUE) -> Invite layout & group page (Not yet) & add group logic & Viewpager2 indicators
3. 2022.02.23(WEN) -> Login Api complete (using Koin, Retrofit2 at Mvvm), profile img bottom sheet & add group logic change (using viewModel)
4. 2022.02.24(THU) -> Refresh token Api, Alarm Check Api, Allow Check Box Logic Change (using viewModel func)
5. 2022.02.25(FRI) -> Api Chekcing
6. 2022.02.26(SAT) -> move Group Page logic change
7. 2022.02.27(SUN) -> Alarm Check Api complete, Response 200 OK -> null check Logic, Add Header

- 3rd Week
1. 2022.02.28(MON) -> reissue Api complete (At Splash), Add Group Bottom Sheet Progress Bar ->  Seek Bar Update
2. 2022.03.01(TUE) -> Not Answer & Not Question Dialog, Home layout update, Seek Bar Divider complete, User Profile Api complete
3. 2022.03.02(WEN) -> Get User api complete, Group Fragment bottom sheet & dialog complete
4. 2022.03.03(THU) -> User profile Api re update, Answer activity publishing, group bottom logic change
5. 2022.03.04(FRI) -> Question & Answer & drink & Edit Drink Activity layout 
6. 2022.03.05(SAT) -> Create Cafe Api complete, Cafe home Api reponse value serialize
7. 2022.03.06(SUN) -> Cafe page Api complete <- (Logic Change use Mvvm & Koin), Not Selected Drink User & Empty recipe list Logic, Answer activity keyboard imset, multiple image select & delete

- 4th Week
1. 2022.03.07(MON) -> Home Api, Cafe Home Api response change, Create Cafe Api response change, Recipe Functional, Home Adapter Logic Change, Move Cafe layout Logic change
2. 2022.03.08(TUE) -> Home Api Group Color, Bookmark Cafe List Api, Select Group Color Change, Splash Logo, Onboarding Activity, Login Layout
3. 2022.03.09(WEN) -> Edit Group & Delete Group Api Complete, Bookmark List at Cafes Api Complete, Question Api, Question text check, Edittext len check Logic change(Rxjava -> not use Rxjava (Why: Not using Overlap api -> Not waste of Network resource))
4. 2022.03.10(THU) -> Search Activity & Api, Firebase connect, Kakao link Complete
5. 2022.03.11(FRI) -> Answer Api Complete, Post Answer Api Complete, Kakao link Test Complete, Search bookmark api complete, Bookmark & Answer adapter logic change
6. 2022.03.12(SAT) -> Add drink api complete, drink recycler complete, Group Frag onResume logic change
7. 2022.03.13(SUN) -> token expire logic ing

- 5th Week
1. 2022.03.14(MON) -> Token expire logic complete, group color change, Post & Get reply api complete, like api complete, Bookmark Api complete
2. 2022.03.15(TUE) -> String to UTF-8, Store Dialog, Question Layout update, Edit & Delete Reply Api complete
3. 2022.03.16(WEN) -> Reply activity move to latest reply update(use coroutine), Recipe & My material & Store & Edit Drink Activity complete
4. 2022.03.17(THU) -> Buy material api complete, My Current Drink Api complete, Layout update, My Material all & integrite Api complete
5. 2022.03.18(FRI) -> Drink Perfomance Update, recipe case update, User Drink Activity Api complete, Edit Drink Api Complete, kakao link custom, Bookmark Search Api (Use Coroutine -> Not input text delay 500 milli seconds)
6. 2022.03.19(SAT) -> Edit Answer Api(~ing), Delete Answer Api
7. 2022.03.20(SUN) -> Drink Finish Layout Complete, Scroll Event, Drink Shadow complete, Drink List & Recipe Functional, Kakao Link Update, Cafe Code ClipManager

- 6th Week
1. 2022.03.21(MON) -> Search Question Api & Layout, Question List Api & Layout
2. 2022.03.22(TUE) -> Recipe Layout Update
3. 2022.03.23(WEN) -> Declare layout, Delete User Api, Empty Layout, Drink resize, Not input nickname user Throw, Use Kakao Link Like Deep Like (Join Cafe at Home)
4. 2022.03.24(THU) -> Declare Api, User Declare List Api & Layout Complete, In App Auto Update Complete (Not yet Test), Group 500 Error Throwable, My Material Count Update, Versio1 release
5. 2022.03.24(FRI) -> Edit Group Error Update, Edit Question text/plain Update, Delete Question Error Update, Error Throwable Invite Cafe 2
6. 2022.03.25(SAT) -> Version2 release, My Question List Error Throwable, Kakao Hash Key Update, Version3 Release
7. 2022.03.26(SUN) -> Ask Question Dialog move to User Question Activity

- 7th Week
1. 2022.03.27(MON) -> Alarm Activity & Alarm Api, Stamp Activity & Stamp Api Complete
2. 2022.03.28(TUE) -> Applying Verision3 Release
3. 2022.03.29(WEN) -> Allow Activity Logic Update, Logo Resize, Splash Dynamic Link Complete
4. 2022.03.30(THU) -> Alarm Api & Read Alarm Api, Layout Update, Not yet Arrived Question Logic
5. 2022.04.01(FRI) -> Version5 Release, Layout update, Answer & Reply length check update, logo size update, Default Profile Error throwable
6. 2022.04.02(SAT) -> Version 7 Release, Version Check Api Update, Layout Update
7. 2022.04.03(SUN) -> Fcm push forground & background, Fcm token api, User Alarm Status Check Api

- 8th Week
1. 2022.04.04(MON) -> Firebase Dynamic Link & Kakao Link Deep link & Fcm push, layout update
2. 2022.04.05(TUE) -> Search Error Throwable, Deep Link Complete, Search Keyboard, Drink Img Update, Firebase Message Complete(Foreground, Background)
3. 2022.04.06(WEN) -> Version 8 Release, Store & Material Top scroll
4. 2022.04.07(THU) -> Drink Error Throwable
5. 2022.04.08(FRI) -> Group Drink Edit

- After Demo Day
1. 2022.04.14(THU) -> Layout Update, Out Cafe User Select Background & text color change
2. 2022.04.15(FRI) -> Reply Scroll Logic Change (Coroutine -> Handler(using Message Queue))
3. 2022.04.24(SUN) -> Multi click block, Auto Search Logic (CoroutineScope -> lifecycleScope)
4. 2022.06.11(SAT) -> Layout Databinding Update
5. 2022.06.15(WEN) -> Databinding until Recipe package 
