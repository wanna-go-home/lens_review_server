package com.springboot.intelllij.domain;

import com.springboot.intelllij.constant.LikeableTables;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "liked_history")
public class LikedHistoryEntity extends BaseEntity {

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "target_table", nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeableTables likeableTable;

    @Column(name = "target_table_content_id", nullable = false)
    private Integer tableContentId;

    public LikedHistoryEntity(Integer accountId, LikeableTables likeableTable, Integer tableContentId) {
        this.accountId = accountId;
        this.likeableTable = likeableTable;
        this.tableContentId = tableContentId;
    }
}
