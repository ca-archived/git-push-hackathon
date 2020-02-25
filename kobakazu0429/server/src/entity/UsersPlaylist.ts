import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  OneToMany
} from "typeorm";
import { User } from "@/entity/User";
import { YoutubeVideo } from "@/entity/YoutubeVideo";

@Entity()
export class UserPlaylist extends BaseEntity {
  @PrimaryGeneratedColumn()
  readonly id!: number;

  @Column({ type: "varchar", length: 255, nullable: false, unique: true })
  youtubePlaylistId!: string;

  @ManyToOne(
    _type => User,
    user => user.playlists
  )
  user!: User;

  @OneToMany(
    _types => YoutubeVideo,
    video => video.playlist
  )
  videos!: YoutubeVideo[];
}
