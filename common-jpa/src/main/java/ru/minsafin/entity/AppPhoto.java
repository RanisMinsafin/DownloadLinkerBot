package ru.minsafin.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "bot", name = "app_photo")
@Entity
public class AppPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telegramFileId;

    @OneToOne
    private BinaryContent binaryContent;

    private String mimeType;

    private Integer photoSize;
}
