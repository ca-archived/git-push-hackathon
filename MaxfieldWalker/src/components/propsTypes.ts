export interface GistCommandProps {
  onMakePublicClick?: () => void;
  onDeleteThisFileClick?: (filenameToDelete: string) => Promise<void>;
  onDeleteThisGistClick: () => Promise<void>;
  updateGistDescription: (description: string) => Promise<void>;
  updateGistFilename: (
    oldFileName: string,
    newFileName: string
  ) => Promise<void>;
}
