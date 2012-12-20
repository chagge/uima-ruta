PACKAGE org.apache.uima;

DECLARE T1, T2, T3, T4, T5, T6, T7, T8;

TYPELIST list1;
TYPELIST list2;
TYPELIST list3;

Document{ -> GETLIST(list1, "Types")};
Document{SIZE(list1,1,1) -> MARK(T1)};

Document{ -> GETLIST(list1, "Types:Begin")};
Document{SIZE(list1,3,3) -> MARK(T2)};

Document{ -> GETLIST(list1, "Types:End")};
Document{SIZE(list1,4,4) -> MARK(T3)};