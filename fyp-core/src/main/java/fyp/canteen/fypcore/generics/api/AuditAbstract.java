package fyp.canteen.fypcore.generics.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;




@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties
public abstract class AuditAbstract extends TimeStampAbstract {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @JsonInclude(JsonInclude.Include.CUSTOM)
    private Long createdBy;

    @LastModifiedBy
    @JsonInclude(JsonInclude.Include.CUSTOM)
    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    @Transient
    private String label;

    @JsonIgnore
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @JsonIgnore
    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLabel() {
        return label;
    }
}

