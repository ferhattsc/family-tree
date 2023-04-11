package com.kou.prolab;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.kou.prolab.UtilClass.parseDateString;
import static com.kou.prolab.UtilClass.yasHesapla;

public class ProlabMainApplication {


    public static void main(String[] args) throws IOException {

        List<Dugum> olusturulanKisiler = new ArrayList<>();

        List<String> filePaths = Arrays.asList("/Users/Ferhat/Desktop/familytree/1.csv",
                "/Users/Ferhat/Desktop/familytree/2.csv",
                "/Users/Ferhat/Desktop/familytree/3.csv",
                "/Users/Ferhat/Desktop/familytree/4.csv");

        soyAgaciniOlustur(filePaths, olusturulanKisiler);

        cocuguOlmayanlariSiralaYazdir(olusturulanKisiler);

        kanGrubuAOlanlariYazdir(olusturulanKisiler);

        soyundaAyniMeslegiYapanCocuklarVeyaTorunlarGosterilecektir(olusturulanKisiler);

        soyAgacindaAyniIsmeSahipKisilerinIsmiVeYaslariGosterilecek(olusturulanKisiler);

        soyAgaciniGoster(olusturulanKisiler);

        System.out.println(olusturulanKisiler.size());
    }

    private static void soyAgaciniGoster(List<Dugum> olusturulanKisiler ) {
        JFrame frame = new JFrame ("AİLE AĞACI");
        frame.setSize(500,800);
        JTextPane textPane = new JTextPane ();

        textPane.setBounds(10,30, 600,12600);
        JScrollPane scroll = new JScrollPane (textPane,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        aileAgaciniDon(olusturulanKisiler, textPane);

        frame.add(scroll);
        frame.setVisible (true);
    }

    private static void appendToPane(JTextPane tp, List<String> msg)
    {
        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);

        String babaIsmi = String.format("Baba: %s \n", msg.get(0));
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(babaIsmi);

        int len1 = tp.getDocument().getLength();
        tp.setCaretPosition(len1);

        String anneIsmi = String.format("Anne: %s \n", msg.get(1));
        StyleContext sc2 = StyleContext.getDefaultStyleContext();
        AttributeSet aset2 = sc2.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.blue);
        aset2 = sc2.addAttribute(aset2, StyleConstants.FontFamily, "Lucida Console");
        aset2 = sc2.addAttribute(aset2, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        tp.setCharacterAttributes(aset2, false);
        tp.replaceSelection(anneIsmi);

        int len2 = tp.getDocument().getLength();
        tp.setCaretPosition(len2);

        String cocuklarinIsmi = String.format("cocuklar-> %s \n\n", msg.get(2));
        StyleContext sc3 = StyleContext.getDefaultStyleContext();
        AttributeSet aset3 = sc3.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.GREEN);
        aset3 = sc3.addAttribute(aset3, StyleConstants.FontFamily, "Lucida Console");
        aset3 = sc3.addAttribute(aset3, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        tp.setCharacterAttributes(aset3, false);
        tp.replaceSelection(cocuklarinIsmi);
    }

    private static void aileAgaciniDon(List<Dugum> olusturulanKisiler, JTextPane jTextPane) {
        List<Dugum> collect = olusturulanKisiler.stream().filter(each -> !each.getChildren().isEmpty()).collect(Collectors.toList());
        List<AileAgaci> aileAgaciList = collect.stream()
                .filter(each -> each.getOwn().getCinsiyet().equals("Erkek") && each.getOwn().getMedeniHali().equals("Evli"))
                .map(each -> AileAgaci.builder()
                        .babaninAdi(each.getOwn().getAd() + " " + each.getOwn().getSoyad())
                        .anneninAdi(each.getOwn().getEsi() + " " + each.getOwn().getSoyad())
                        .cocuklarinAdi(getCocuklarinIsmi(each))
                .build())
                .collect(Collectors.toList());

        aileAgaciList.forEach(aileAgaci -> appendToPane(jTextPane, Arrays.asList(aileAgaci.getBabaninAdi(), aileAgaci.getAnneninAdi(), getAileAgaciString(aileAgaci))));
    }

    private static String getAileAgaciString(AileAgaci aileAgaci) {
        String cocukIsimleri = "";
        for (String str : aileAgaci.getCocuklarinAdi()) {
            cocukIsimleri = cocukIsimleri.concat(str);
        }
        return cocukIsimleri;
    }

    private static List<String> getCocuklarinIsmi(Dugum dugum) {
        return new ArrayList<>(dugum.getChildren()).stream().map(each -> each.getAd() + " " + each.getSoyad() + " ").collect(Collectors.toList());
    }

    private static void soyAgaciniOlustur(List<String> filePaths, List<Dugum> olusturulanKisiler){
        List<Kisi> kisiler = csvOkuma(filePaths);
        if (!kisiler.isEmpty()) {
            kisiler.forEach(each -> {
                soyAgaciniOlustur(kisiler.stream().filter(each1 -> each != each1).collect(Collectors.toList()), each, olusturulanKisiler);
            });
        }
    }

    private static void soyAgaciniOlustur(List<Kisi> kisiler, Kisi kisi, List<Dugum> olusturulanKisiler) {
        kisiler.forEach(each -> {
            List<Dugum> collect = olusturulanKisiler.stream()
                    .filter(each5 -> each5.getOwn() == kisi)
                    .collect(Collectors.toList());

            if (kisi.getDogumTarihi().before(each.getDogumTarihi())) {
                if(kisi.getCinsiyet().equals("Erkek") && each.getBabaAdi().equals(kisi.getAd()) && each.getAnneAdi().equals(kisi.getEsi())
                        || kisi.getCinsiyet().equals("Kadın") && each.getAnneAdi().equals(kisi.getAd()) && each.getBabaAdi().equals(kisi.getEsi())){
                    addchildren(collect, kisi, each, olusturulanKisiler);
                }
            }else {
                if (collect.isEmpty()) {
                    Dugum dugum = new Dugum(kisi);
                    olusturulanKisiler.add(dugum);
                }
            }
        });
    }

    private static void addchildren(List<Dugum> collect, Kisi kisi, Kisi each, List<Dugum> olusturulanKisiler) {
        if (collect.isEmpty()) {
            Dugum dugum = new Dugum(kisi);
            dugum.getChildren().add(each);
            olusturulanKisiler.add(dugum);
        } else {
            Dugum dugum = collect.get(0);
            dugum.getChildren().add(each);
        }
    }

    private static void cocuguOlmayanlariSiralaYazdir(List<Dugum> olusturulanKisiler) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Cocugu Olmayanlarin Yaslarina Göre Siralamasi -> ");
        List<Dugum> cocuguOlmayanlar = olusturulanKisiler.stream().filter(each -> each.getChildren().isEmpty()).collect(Collectors.toList());

        List<Kisi> cocuguOlmayanlarListesi = new ArrayList<>();

        cocuguOlmayanlar.forEach(each -> cocuguOlmayanlarListesi.add(each.getOwn()));

        cocuguOlmayanlarListesi.stream().sorted(Comparator.comparing(Kisi::getDogumTarihi)).forEach(each -> System.out.println(each.getAd() + " " + each.getSoyad() + "      " + yasHesapla(each.getDogumTarihi()) + "      " + each.getDogumTarihi()));
        System.out.println("---------------------------------------------------------------------------------");
    }

    private static List<Dugum> uveyKardesleriBulHarfSirasinaGoreKaydet() {
        return null;
    }

    private static void kanGrubuAOlanlariYazdir(List<Dugum> olusturulanKisiler) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Kan Grubu A(+) Olanlar -> ");
        List<Dugum> aKanGrubuOlanlar = olusturulanKisiler.stream().filter(each -> each.getOwn().getKanGrubu().contains("A(+)")).collect(Collectors.toList());
        aKanGrubuOlanlar.forEach(each -> System.out.println(each.getOwn().getAd() + " " + each.getOwn().getSoyad() + " " + each.getOwn().getKanGrubu()));
        System.out.println("---------------------------------------------------------------------------------");
    }

    private static void soyundaAyniMeslegiYapanCocuklarVeyaTorunlarGosterilecektir(List<Dugum> olusturulanKisiler) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Soyunda Ayni Meslegi Yapanlar -> ");


        List<Kisi> kisilerList = new ArrayList<>();

        olusturulanKisiler.forEach(each -> kisilerList.add(each.getOwn()));

        List<Kisi> erkekList = kisilerList.stream().filter(each -> each.getCinsiyet().equals("Erkek")).collect(Collectors.toList());

        erkekList.forEach(each -> {
            ayniMeslekteBulunanAileBireyleriniYazdir(erkekList.stream().filter(each1 -> each != each1).collect(Collectors.toList()), each);
        });

        System.out.println("---------------------------------------------------------------------------------");
    }

    private static void ayniMeslekteBulunanAileBireyleriniYazdir(List<Kisi> erkekList, Kisi kisi) {
        List<Kisi> kisiArrayList = new ArrayList<>();
        erkekList.forEach(each -> {
            if (kisi.getSoyad().equals(each.getSoyad()) && kisi.getMeslekOzellikleri().equals(each.getMeslekOzellikleri())){
                kisiArrayList.add(kisi);
            }
        });

        List<Kisi> uniqList = kisiArrayList.stream().distinct().collect(
                Collectors.toList());

        uniqList.forEach(each -> {
                if (!StringUtils.isBlank(each.getMeslekOzellikleri()) && !each.getMeslekOzellikleri().contains("Öğrenci")) {
                    System.out.println(each.getDogumTarihi() + " " + each.getAd() + " " + each.getSoyad() + " " + each.getMeslekOzellikleri());
                }
        });
    }

    private static void soyAgacindaAyniIsmeSahipKisilerinIsmiVeYaslariGosterilecek(List<Dugum> olusturulanKisiler) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Soy Agacinda Ayni Isme Sahip Kisiler Ve Yaslari -> ");


        List<Kisi> kisilerList = new ArrayList<>();

        olusturulanKisiler.forEach(each -> kisilerList.add(each.getOwn()));

        kisilerList.forEach(each -> {
            soyAgacindaAyniIsmeSahipKisilerinIsmiVeYaslariYazdir(kisilerList.stream().filter(each1 -> each != each1).collect(Collectors.toList()), each);
        });

        System.out.println("---------------------------------------------------------------------------------");
    }

    private static void soyAgacindaAyniIsmeSahipKisilerinIsmiVeYaslariYazdir(List<Kisi> kisilerList, Kisi kisi) {

        List<Kisi> kisiArrayList = new ArrayList<>();
        kisilerList.forEach(each -> {
            if (kisi.getAd().equals(each.getAd())){
                kisiArrayList.add(kisi);
            }
        });

        List<Kisi> uniqList = kisiArrayList.stream().distinct().collect(
                Collectors.toList());

        uniqList.stream().sorted(Comparator.comparing(Kisi::getAd)).forEach(each -> System.out.println(each.getAd() + " " + each.getSoyad() + " " + yasHesapla(each.getDogumTarihi())));
    }

    private static List<Dugum> kullanicidanAlinacak2IsmeGoreBuyugununKucuguneYakinligiGosterilecektir() {
        // Kişinin annesinin annesinin babası gibi
        return null;
    }

    private static List<Dugum> kisiBilgisineGoreOKisiyeAitSoyAgacininGosterilmesiVeDerecelerininYazilmasi() {
        // Kişinin annesinin annesinin babası gibi -> soy agacinda ki
        return null;
    }

    private static List<Dugum> soyAgacininKacNesildenOlustugunuBul() {
        return null;
    }

    private static List<Dugum> ismeGoreOIsimdenSonraKacNesilGeldiginiBul() {
        return null;
    }

    private static List<Kisi> csvOkuma(List<String> filePaths) {
        List<Kisi> kisiler = new ArrayList<>();
        if (!filePaths.isEmpty()){
            filePaths.forEach(each ->  {
                Scanner scanner = null;
                try {
                    scanner = new Scanner(new File(each));
                    int rowIndex = 0;
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (rowIndex > 0) {
                            String[] split = line.split(";");
                            if (split.length > 0){
                                List<Kisi> collect = kisiler.stream().filter(kisi -> Objects.equals(kisi.getTcNo(), Long.valueOf(split[0]))).collect(Collectors.toList());
                                if (!collect.isEmpty()) {
                                    continue;
                                }
                                Kisi kisi = Kisi.builder()
                                        .tcNo(Long.valueOf(split[0]))
                                        .ad(split[1])
                                        .soyad(split[2])
                                        .dogumTarihi(parseDateString(split[3]))
                                        .esi(getEsAdi(split[4]))
                                        .anneAdi(split[6])
                                        .babaAdi(split[7])
                                        .kanGrubu(split[8])
                                        .meslekOzellikleri(split[9])
                                        .medeniHali(split[10])
                                        .kizlikSoyadi(split[11])
                                        .cinsiyet(split[12])
                                        .build();

                                kisiler.add(kisi);
                            }
                        }
                        rowIndex++;
                    }
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        return kisiler;
    }

    private static String getEsAdi(String fullName) {
        return Arrays.stream(fullName.split(" ")).findFirst().orElse("");
    }
}
