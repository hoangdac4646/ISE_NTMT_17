﻿Góp ý

1. Về trình bày:
 - Sửa logo (Lấy logo chương trình trong thư mục 03_Design/User Interface/Images/Icon/AppIcon.png), chú ý xoá hoàn toàn logo cũ đi.
 - Viết đúng chính tả, không viết tắt như ,... vs - yêu cầu viết rõ toàn bộ).
 - Dùng font Roboto hoặc Segoe ui
 - Tiêu đề "Sơ quát" sửa thành "Sơ bộ".
 - Nên bỏ các từ "có thể", "là" để tránh lủng củng câu.
 - Không sử dụng các đại từ nhân xưng như "mình", "ta", "họ", "nó",... (Điều này là cơ bản trong cách viết báo cáo).
 - Viết ngắn gọn, không lặp ý, ưu tiên dùng từ chuyên ngành (VD: Có thể đăng nhập tài khoản của mình ở một máy khác chứ không phải máy mình -> Đăng nhập được trên nhiều thiết bị, - Phần mềm có thể dể dàng quản lý số tiền hiện có của chính bản thân họ -> Phần mềm quản lý chi tiêu thực tế cá nhân.)

2. Về nội dung:
 - Theo yêu cầu khách hàng, tiền có mục đích khác nhau không phải phân ra loại "ví" riêng biệt mà gọi đó là một khoản thu/chi. Tức là chỉ có một ví duy nhất, khách hàng thu/chi bằng cách tạo ra một khoản thu/chi thông qua việc nhập số tiền kèm mô tả là tiêu cho mục đích gì.
 - Nếu là tạo nhiều ví khác nhau thì cần phân loại theo các tính chất phân biệt rõ ràng như tính chất vật lí:tiền mặt, thẻ ngân hàng, visa,...
 - Đặt tên các yêu cầu dễ hiểu, dễ nhận biết. Nên đặt theo R_Động-từ-mô-tả-chức-năng. (VD: R1 -> R_LogSign, R2 -> R_ManageWalletInfo, R3 -> R_AddTransaction,...). Yêu cầu đặt để dev có thể đọc và hiểu dễ dàng, có thể đặt dài nhưng phải là gọn gàng và dễ hiểu nhất,

Phẩn Hồi
- Sửa logo (Lấy logo chương trình trong thư mục 03_Design/User Interface/Images/Icon/AppIcon.png), chú ý xoá hoàn toàn logo cũ đi
=>Chấp Nhận
-Viết đúng chính tả, không viết tắt như ,... vs - yêu cầu viết rõ toàn bộ).
=>Chấp Nhận
- Tiêu đề "Sơ quát" sửa thành "Sơ bộ".
=>Chấp Nhận
- Không sử dụng các đại từ nhân xưng như "mình", "ta", "họ", "nó",... (Điều này là cơ bản trong cách viết báo cáo).
=>Chấp Nhận
- Viết ngắn gọn, không lặp ý, ưu tiên dùng từ chuyên ngành (VD: Có thể đăng nhập tài khoản của mình ở một máy khác chứ không phải máy mình -> Đăng nhập được trên nhiều thiết bị, - Phần mềm có thể dể dàng quản lý số tiền hiện có của chính bản thân họ -> Phần mềm quản lý chi tiêu thực tế cá nhân.)
=> Không chấp nhân ở câu "ưu tiên dùng từ chuyên ngành" vì đây la báo cáo requirement có thể khách hàng đọc và không hiểu sẽ gây mất nhiều thời gian và khiến khách hàng bị khố hiểu.
-Theo yêu cầu khách hàng, tiền có mục đích khác nhau không phải phân ra loại "ví" riêng biệt mà gọi đó là một khoản thu/chi. Tức là chỉ có một ví duy nhất, khách hàng thu/chi bằng cách tạo ra một khoản thu/chi thông qua việc nhập số tiền kèm mô tả là tiêu cho mục đích gì.
=>Đã đổi thành định nghĩ "ví" thành khoản thu/chi.
-Nếu là tạo nhiều ví khác nhau thì cần phân loại theo các tính chất phân biệt rõ ràng như tính chất vật lí:tiền mặt, thẻ ngân hàng, visa,...
=> khách hàng không yêu cầu phải phân loại tính chất vật liệu.
-Đặt tên các yêu cầu dễ hiểu, dễ nhận biết. Nên đặt theo R_Động-từ-mô-tả-chức-năng. (VD: R1 -> R_LogSign, R2 -> R_ManageWalletInfo, R3 -> R_AddTransaction,...). Yêu cầu đặt để dev có thể đọc và hiểu dễ dàng, có thể đặt dài nhưng phải là gọn gàng và dễ hiểu nhất,
=> Sai quy định đặt tên theo chuẩn mà giáo viên yêu cầu. Chí cần R1, R2 hoặc la 1 2 3 là được rồi không cần thiết phải đặt dài dòng thế.