package org.kesler.fiastester.logic;

import org.kesler.fiastester.dao.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Алексей on 01.02.14.
 */
@Entity
@Table(name = "FIASRecords")
public class FIASRecord extends AbstractEntity{

    @Column(name = "AOGUID", length = 36)
    private String aoGuid;

    @Column(name = "PARENTGUID", length = 36)
    private String parentGuid;

    @Column(name = "FORMALNAME", length = 120)
    private String formalName;

    @Column(name = "SHORTNAME", length = 10)
    private String shortName;

}
