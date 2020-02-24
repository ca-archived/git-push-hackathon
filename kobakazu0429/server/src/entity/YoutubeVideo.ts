import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne
} from "typeorm";
import { UserPlaylist } from "@/entity/UsersPlaylist";

@Entity()
export class YoutubeVideo extends BaseEntity {
  @PrimaryGeneratedColumn()
  readonly id!: number;

  @Column({ type: "varchar", length: 255, nullable: false, unique: true })
  youtubeVideoId!: string;

  // "real" is float(e.g. 0.76) in sqlite
  @Column({ type: "real", nullable: true, default: null })
  start!: number;

  @Column({ type: "real", nullable: true, default: null })
  end!: number;

  @ManyToOne(
    _type => UserPlaylist,
    playlist => playlist.videos
  )
  playlist!: UserPlaylist;
}
