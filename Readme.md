# DỰ ÁN CHO CUỘC THI [PROGAMECUP]
## 1. Thông Tin Nhóm

**Tên Dự Án:** [SODUTA]

**Link Dự Án:** [[GitHub Link](https://github.com/Yamaaaaaaaa/Group5_BTCK_PGC-Endless_Way.git)](#)

**Thành Viên Nhóm:**
- [Trần Xuân Sơn]
- [Phan Thanh Tân]
- [Phan Văn Duy]

**Mentor:**
- [Triệu Ngọc Tâm]
- [Nguyễn Quốc Hưng]


### Mô hình làm việc

Team hoạt động theo mô hình Scrum, sử dụng Linear để quản lý công việc. Các công việc được keep track đầy đủ trên Linear.
- Link linear: [[Linear N5](https://linear.app/pgjbdtproptit-part2/team/SDT/projects/active?fbclid=IwAR1ESP9KFQGMWj93qBpzXabN2zeH_5-VbZpe1nH1J-7E2O_D11sDfYcCkHY)](#)

Mỗi tuần, team sẽ ngồi lại để review công việc đã làm, cùng nhau giải quyết vấn đề và đề xuất giải pháp cho tuần tiếp theo. Sau đó sẽ có buổi demo cho mentor để nhận phản hồi và hướng dẫn.

### Version Control Strategy


Team hoạt động theo Gitflow để quản lý code. Mỗi thành viên sẽ tạo branch từ `develop` để làm việc, các branch đặt theo format `feature/ten-chuc-nang`, sau khi hoàn thành sẽ tạo Pull Request để review code và merge vào develop
- Các nhánh chính:
  - `master`: Chứa code ổn định, đã qua kiểm tra và test kỹ lưỡng
  - `develop`: Chứa code mới nhất, đã qua review và test
  - `feature/`: Các nhánh chứa code đang phát triển, short-live, sau khi hoàn thành sẽ merge vào `develop`. 

![alt text](image.png)

Sau mỗi tuần, team sẽ merge `develop` vào `master` để release phiên bản mới.



## 2. Giới Thiệu Dự Án

**MÔ TẢ:** **[SODUTA]** là 1 tựa game sinh tồn dựa trên ý tưởng thời đại mà đại dịch Zombie càn quét khắp nơi trên thế giới. Nhân vật chính có nhiệm vụ phải tiêu diệt càng nhiều Zombie càng tốt để bảo vệ Trái Đất.

## 3. Các Chức Năng Chính

- Berserk Mode
- Các cơ chế tấn công: Bằng kiếm, súng
- Cơ chế lưu File, lưu Ranking
- Hệ thống âm nhạc

## 4. Công nghệ

### 4.1. Công Nghệ Sử Dụng
- **LibGDX**: 
- **Tiled**: Sử dụng để tạo MAP. 

### 4.2 Cấu trúc dự án

```
- assets 
  - Apocalypse Character Pack
  - basic
  - button
  - file
  - fonts
  - music
  - ui
- core
  - build
  - src/com/mygdx/game
    - controller
    - model
    - setting
    - view
    - SpaceGame.java
- desktop
  - build
  - src/com/mygdx/game
    - DesktopLauncher.java
```

Diễn giải:
- **assets:** Chứa các tài nguyên như hình ảnh, âm thanh
- **core:** Chứa các class chính của game như model, view, controller
- **desktop** Chứa các class để chạy trên các nền tảng khác nhau"
- ...
## 5. Ảnh và Video Demo

**Ảnh Demo:**
![Ảnh Demo](#)

**Video Demo:**
[Video Link](#)






## 6. Các Vấn Đề Gặp Phải

### Vấn Đề 1: [Kiểm tra va chạm]
**Ví dụ:** Kiểm tra va chạm với vật thể của LibGDX không được hỗ trợ, Code chay sẽ rất mất thời gian

### Hành Động Để Giải Quyết

**Giải pháp:** Với mỗi *Tile* mà có va chạm, Nhóm đã thêm 1 *Property* = blocked. Khi kiểm tra va chạm, chỉ cần check *Property* là được.


### Vấn Đề 2: [Di chuyển nhân vật và các vật thể khác]
**Ví dụ:** Có rất nhiều vật thể có thể di chuyển: Nhân vật, Quái, Đạn,.... Chúng nó những thuộc tính giống nhưng cũng có những cái khác nhau


### Hành Động Để Giải Quyết

**Giải pháp:** Tạo 1 Interface Gốc có những phương thức bắt buộc (Movement) và implement với từng vật thể khác nhau

### Kết Quả

- Đã có thể quản lý dễ dàng việc di chuyển của nhân vật.

### Vấn Đề 3: [Xử lý Sinh quái]
**Ví dụ:** Quái sinh rải rác, không cố định do phương thức sinh chưa hợp lý


### Hành Động Để Giải Quyết

**Giải pháp:** Chia map thành nhiều khu vực, mỗi Khu vực sẽ có 1 vài điểm sinh quái cố định. Nếu nhân vật di chuyển sang khu vực nào thì sẽ chỉ sinh quái ở khu vực đó và 1 số khu lân cận

### Kết Quả

- Có thể kiểm soát được lượng quái cùng với khu vực sinh quái. Từ đó có thể làm khó người chơi bằng những khu vực đặc biệt khó.

## 7. Kết Luận

**Kết quả đạt được:** Game của nhóm đã được cải thiện rất nhiều về hiệu năng, lỗi chơi, logic game ,...

**Hướng phát triển tiếp theo:** 
- Tạo thêm các Map mới
- Tạo thêm các Quái, Vũ Khí, Nhân Vật mới.
- Thêm các chức năng cho Berserk Mode.
- Thêm khả năng đọ bảng Rank với các đối thủ trên khắp thế giới.
