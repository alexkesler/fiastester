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
    private String aoGUID;

    @Column(name = "PARENTGUID", length = 36)
    private String parentGUID;

    @Column(name = "FORMALNAME", length = 120)
    private String formalName;

    @Column(name = "SHORTNAME", length = 10)
    private String shortName;

    @Column(name = "REGIONCODE", length = 2)
    private String regionCode;

    @Column(name = "AOLEVEL", length = 10)
    private String aoLevel;


    public String getAoGUID() {return aoGUID;}

    public void setAoGUID(String aoGUID) {this.aoGUID = aoGUID;}

    public String getParentGUID() {return parentGUID;}

    public void setParentGUID(String parentGUID) {this.parentGUID = parentGUID;}

    public String getFormalName() {return formalName;}

    public void setFormalName(String formalName) {this.formalName = formalName;}

    public String getShortName() {return shortName;}

    public void setShortName(String shortName) {this.shortName = shortName;}

    public String getRegionCode() {return regionCode;}

    public void setRegionCode(String regionCode) {this.regionCode = regionCode;}

    public String getAoLevel() {return aoLevel;}

    public void setAoLevel(String aoLevel) {this.aoLevel = aoLevel;}




}
