package com.mycompany.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DropboxUploader {

    private static final String ACCESS_TOKEN = "sl.BdxvRnd-AQHWdPQGUO2FmIpYQD5AJiD8cjKx2VrXqfp90SWMfMxDBxC1qqXpyAFpWRh6HJo6C2OIlFyeTnB0Dpv6gNLUzK0zwNRtq5oHhMidsWUsXaNweAk1nHsixvyWMAny_4K8PTc";

    public static void main(String[] args) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-example").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        String localFilePath = "path/to/local/file.txt";
        String dropboxPath = "/path/in/dropbox/file.txt";

        try (InputStream in = new FileInputStream(localFilePath)) {
            FileMetadata metadata = client.files().uploadBuilder(dropboxPath)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);
            System.out.println("Fichier téléversé : " + metadata.getPathLower());
        } catch (FileNotFoundException e) {
            System.err.println("Fichier local introuvable : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur d'entrée/sortie : " + e.getMessage());
        } catch (DbxException e) {
            System.err.println("Erreur Dropbox API : " + e.getMessage());
        }
    }
}
