package com.springboot.intelllij.domain;

import com.springboot.intelllij.constant.LikeableTables;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "liked_history")
public class LikedHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

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
