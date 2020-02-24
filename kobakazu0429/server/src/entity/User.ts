import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  OneToMany
} from "typeorm";
import { UserPlaylist } from "@/entity/UsersPlaylist";

@Entity()
export class User extends BaseEntity {
  @PrimaryGeneratedColumn()
  id!: number;

  @Column({ type: "varchar", length: 255, nullable: false })
  googleId!: string;

  @OneToMany(
    _type => UserPlaylist,
    playlist => playlist.user
  )
  playlists!: UserPlaylist[];
}
