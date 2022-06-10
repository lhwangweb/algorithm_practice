# 個人演算法練習

個人在練習演算法過程所寫的實作，每個主題一個檔案。

這邊沒有特別把程式碼整理為教材或者範例的形式，僅把思考過程、筆記注釋以註解的形式留下，歡迎參考。

## 河內塔 (Python 3)
 - 實作一個 class 作為柱子。
 - 柱子 class 擁有一個 property 是 int list，作為堆放盤子的 Stack 。
 - 盤子以 list 內的 int 代表， int 數字即盤子的寬度
 - class 含有 pop push 等 method，可以把盤子移來移去，整個遞迴的程式碼就是在三根柱子(instance) 之間不停的 pop、push 盤子。
 - push、pop 等動作遵守河內塔的規則，大的不能疊在小的上面。對應到 list 上， list index 越小代表柱子的越下方，換言之 list 永遠嚴格遞減排序(strictly DESC)

## 選擇排序 (Python 3)
1. 非 in-place
 - for loop 從 N 個內挑出最大/最小，放到新 list，全部走過一次後，排序就完成了。

2. in-place 形式

TODO

## 遞迴 (Python 3)
 - 遞迴練習

## 快速排序 (Python 3)
練習了三種形式：
1. 每次 D&C 切割的子任務都放到新的 list (記憶體) 內 (好處是很單純，很好寫。 壞處就是空間複雜度較大)
2. 在原本的 list 上原地排列，每次 D&C 切割的子任務，都是以 index 來標記該任務的範圍，而 Element 的挪動則靠交換。
3. 同上，在原本 list 上原地操作，並以交換來挪動排序，不過交換的演算法不同。

有加入簡單的 test

## 合併排序 (Python 3)
練習以下形式：
1. 每次 merge 子任務結果時，都放到新的 list (記憶體) 內 (好處是很單純，很好寫。 壞處就是空間複雜度較大)

有加入簡單的 test，含 sort stability 的相關測試 (以 dict list 排序，並跟 bubble sort 的結果比，能 PASS assertEqual)

## Dijkstra (Python 3)
參考 [此 Youtube 教學](https://www.youtube.com/watch?v=JLARzu7coEs) ，以教學案例的無向圖練習。

## 共用子字串 Common Substring
1. 以 Dynamic Programing 找出兩個字串的最長共用字串 (the longest common substring)
2. 以 Dynamic Programing 找出兩個字串的所有共用字串 (all common substrings)
