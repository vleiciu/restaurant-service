ALTER TABLE ITEMS ADD CONSTRAINT ITEM_LINE_FK FOREIGN KEY (LINE_ID) REFERENCES LINE_ITEMS (LINE_ID);
ALTER TABLE LINE_ITEMS ADD CONSTRAINT LINE_ITEM_FK FOREIGN KEY (ITEM_ID) REFERENCES ITEMS (ITEM_ID);