package ru.astronarh.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "crossword")
public class Crossword {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="crossword_id_seq")
    @SequenceGenerator(name="crossword_id_seq", sequenceName="crossword_id_seq", allocationSize=1)
    private int id;

    @Basic
    @Column(name = "rows")
    private int rows;

    @Basic
    @Column(name = "columns")
    private int columns;

    @Basic
    @Column(name = "idbegin")
    private int idBegin;

    @Basic
    @Column(name = "idend")
    private int idEnd;

    @Basic
    @Column(name = "created_on")
    private Timestamp createdOn;

    public Crossword(int rows, int columns, int idBegin, int idEnd) {
        this.rows = rows;
        this.columns = columns;
        this.idBegin = idBegin;
        this.idEnd = idEnd;
    }

    public Crossword() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getIdBegin() {
        return idBegin;
    }

    public void setIdBegin(int idBegin) {
        this.idBegin = idBegin;
    }

    public int getIdEnd() {
        return idEnd;
    }

    public void setIdEnd(int idEnd) {
        this.idEnd = idEnd;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Crossword{" +
                "id=" + id +
                ", rows=" + rows +
                ", columns=" + columns +
                ", idBegin=" + idBegin +
                ", idEnd=" + idEnd +
                ", createdOn=" + createdOn +
                '}';
    }
}
