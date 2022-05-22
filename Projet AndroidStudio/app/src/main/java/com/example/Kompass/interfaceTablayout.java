package com.example.Kompass;

public interface interfaceTablayout {
    void onJsonReceived(String json);
    void onCraft(Boolean isCraft);
    void onProgressUpdate(int progressEndurance, int progressFaim, int progressSante);
}
