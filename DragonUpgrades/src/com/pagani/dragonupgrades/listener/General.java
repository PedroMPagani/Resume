package com.pagani.dragonupgrades.listener;

import com.massivecraft.factions.entity.MPlayer;
import com.pagani.dragonupgrades.Main;
import com.pagani.dragonupgrades.features.Menu;
import com.pagani.dragonupgrades.object.User;
import com.pagani.dragonupgrades.sql.AtlasStorage;
import com.pagani.dragonupgrades.utils.Banner;
import com.pagani.dragonupgrades.utils.ItemBuilder;
import nuvemplugins.solaryeconomy.app.SolaryEconomy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.jnlp.UnavailableServiceException;
import java.math.BigDecimal;
import java.util.Collections;

public class General implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (mPlayer.hasFaction()){
            User user = Main.cache.get(mPlayer.getFaction().getName());
            if (user == null)  AtlasStorage.loadFac(mPlayer.getFactionName());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (mPlayer.hasFaction()){
            if (Main.cache.containsKey(mPlayer.getFaction().getName())) {
                if (mPlayer.getFaction().getOnlinePlayers().containsAll(Collections.singleton(e.getPlayer()))) {
                    AtlasStorage.unLoadFac(mPlayer.getFactionName());
                }
            }
        }
    }


    @EventHandler
    public void onCLick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equalsIgnoreCase("Tipos de Upgrade")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
            if (!Main.cache.containsKey(MPlayer.get(p).getFactionName())) return;
            User user = Main.cache.get(MPlayer.get(p).getFactionName());
            double coin = SolaryEconomy.economia.getBalance(p.getName()).doubleValue();
            MPlayer mPlayer = MPlayer.get(p);
                    if (e.getRawSlot() == 10) {
                        if (user.isNivel1()) {
                            p.sendMessage("??eSua fac????o j?? tem este n??vel de upgrade.");
                        } else {
                            if (coin >= 200000) {
                                SolaryEconomy.economia.substractBalance(p.getName(), BigDecimal.valueOf(200000));
                                p.sendMessage("??eUpgrade realizado com sucesso.");
                                user.setNivel1(true);
                                ItemStack item1 = new ItemBuilder(Material.GOLD_INGOT).setName("??eN??vel I").setLore("??7Fa??a um upgrade da sua fac????o", "??7para receber v??rias vantagens.", "??7listadas abaixo."
                                        , "??fVantagens:", "", "??e ??? ??7Aumenta +1 vagas na fac????o;",
                                        "??e ??? ??7Adiciona +3% a mais em toda venda de item na loja;",
                                        "??e ??? ??7Adiciona +10% em drops de mobs;",
                                        "??e ??? ??7Acrescenta +10% de xp em habilidades.", "", "??fPre??o: ??7200.000",
                                        "", (coin >= 200000 && !user.isNivel1() ? "??cClique para comprar a evolu????o." : (user.isNivel1() ? "??eVoc?? j?? possui essa evolu????o" : "??cVoc?? n??o possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(e.getRawSlot(), item1);
                                ItemStack item8 = new ItemBuilder(Banner.getRedBanner(MPlayer.get(p).getFactionTag())).setLore("??7Status geral dos n??veis:", "",
                                        (user.isNivel1() ? "??fN??vel I: ??aConquistado." : "??fN??vel I: ??cN??o Conquistado."),
                                        (user.isNivel2() ? "??fN??vel II: ??aConquistado." : "??fN??vel II: ??cN??o Conquistado."),
                                        (user.isNivel3() ? "??fN??vel III: ??aConquistado." : "??fN??vel III: ??cN??o Conquistado."),
                                        (user.isNivel4() ? "??fN??vel IV: ??aConquistado." : "??fN??vel IV: ??cN??o Conquistado."),
                                        (user.isNivel5() ? "??fN??vel V: ??aConquistado." : "??fN??vel V: ??cN??o Conquistado."),
                                        (user.isNivel6() ? "??fN??vel VI: ??aConquistado." : "??fN??vel VI: ??cN??o Conquistado."),
                                        (user.isNivel7() ? "??fN??vel VII: ??aConquistado." : "??fN??vel VII: ??cN??o Conquistado.")
                                ).setName("??eStatus sua fac????o").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(29, item8);
                                p.updateInventory();
                                mPlayer.getFaction().setMemberBoost(mPlayer.getFaction().getMemberBoost() + 1);
                                Long sys2 = System.currentTimeMillis();
                                AtlasStorage.saveFac(mPlayer.getFactionName(), user, true);
                                return;
                            } else p.sendMessage("??cVoc?? precisa de ??7" + "200.000??c coins.");
                        }
                        return;
                    }
                    if (e.getRawSlot() == 11) {
                        if (user.isNivel2()) {
                            p.sendMessage("??eSua fac????o j?? tem este n??vel de upgrade.");
                        } else {
                            if (!user.isNivel1()) {
                                p.sendMessage("??cSua fac????o precisa do upgrade antigo.");
                                return;
                            }
                            if (coin >= 400000) {
                                SolaryEconomy.economia.substractBalance(p.getName(), BigDecimal.valueOf(400000));
                                p.sendMessage("??eUpgrade realizado com sucesso.");
                                user.setNivel2(true);
                                ItemStack item2 = new ItemBuilder(Material.DIAMOND_PICKAXE).setName("??eN??vel II").setLore("??7Fa??a um upgrade da sua fac????o", "??7para receber v??rias vantagens.", "??7listadas abaixo."
                                        , "??fVantagens:", "", "??e ??? ??7Aumenta +1 vagas na fac????o;",
                                        "??e ??? ??7Adiciona +4% a mais em toda venda de item na loja;",
                                        "??e ??? ??7Adiciona +15% em drops de mobs;",
                                        "??e ??? ??7Acrescenta +20% de xp em habilidades.", "", "??fPre??o:??7 400.000",
                                        "", (coin >= 400000 && !user.isNivel2() ? "??cClique para comprar a evolu????o." : (user.isNivel2() ? "??eVoc?? j?? possui essa evolu????o" : "??cVoc?? n??o possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(e.getRawSlot(), item2);
                                ItemStack item8 = new ItemBuilder(Banner.getRedBanner(MPlayer.get(p).getFactionTag())).setLore("??7Status geral dos n??veis:", "",
                                        (user.isNivel1() ? "??fN??vel I: ??aConquistado." : "??fN??vel I: ??cN??o Conquistado."),
                                        (user.isNivel2() ? "??fN??vel II: ??aConquistado." : "??fN??vel II: ??cN??o Conquistado."),
                                        (user.isNivel3() ? "??fN??vel III: ??aConquistado." : "??fN??vel III: ??cN??o Conquistado."),
                                        (user.isNivel4() ? "??fN??vel IV: ??aConquistado." : "??fN??vel IV: ??cN??o Conquistado."),
                                        (user.isNivel5() ? "??fN??vel V: ??aConquistado." : "??fN??vel V: ??cN??o Conquistado."),
                                        (user.isNivel6() ? "??fN??vel VI: ??aConquistado." : "??fN??vel VI: ??cN??o Conquistado."),
                                        (user.isNivel7() ? "??fN??vel VII: ??aConquistado." : "??fN??vel VII: ??cN??o Conquistado.")
                                ).setName("??eStatus sua fac????o").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(29, item8);
                                p.updateInventory();
                                mPlayer.getFaction().setMemberBoost(mPlayer.getFaction().getMemberBoost() + 1);
                                AtlasStorage.saveFac(mPlayer.getFactionName(), user, true);
                                return;
                            } else p.sendMessage("??cVoc?? precisa de ??7" + "400.000??c coins.");
                        }
                        return;
                    }
                    if (e.getRawSlot() == 12) {
                        if (user.isNivel3()) {
                            p.sendMessage("??eSua fac????o j?? tem este n??vel de upgrade.");
                        } else {
                            if (!user.isNivel2()) {
                                p.sendMessage("??cSua fac????o precisa do upgrade antigo.");
                                return;
                            }
                            if (coin >= 500000) {
                                SolaryEconomy.economia.substractBalance(p.getName(), BigDecimal.valueOf(500000));
                                p.sendMessage("??eUpgrade realizado com sucesso.");
                                user.setNivel3(true);
                                ItemStack item3 = new ItemBuilder(Material.DIAMOND_SWORD).setName("??eN??vel III").setLore("??7Fa??a um upgrade da sua fac????o", "??7para receber v??rias vantagens.", "??7listadas abaixo."
                                        , "??fVantagens:", "", "??e ??? ??7Aumenta +1 vagas na fac????o;",
                                        "??e ??? ??7Adiciona +5% a mais em toda venda de item na loja;",
                                        "??e ??? ??7Adiciona +20% em drops de mobs;",
                                        "??e ??? ??7Acrescenta +30% de xp em habilidades.", "", "??fPre??o: ??7500.000",
                                        "", (coin >= 500000 && !user.isNivel3() ? "??cClique para comprar a evolu????o." : (user.isNivel3() ? "??eVoc?? j?? possui essa evolu????o" : "??cVoc?? n??o possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(e.getRawSlot(), item3);
                                ItemStack item8 = new ItemBuilder(Banner.getRedBanner(MPlayer.get(p).getFactionTag())).setLore("??7Status geral dos n??veis:", "",
                                        (user.isNivel1() ? "??fN??vel I: ??aConquistado." : "??fN??vel I: ??cN??o Conquistado."),
                                        (user.isNivel2() ? "??fN??vel II: ??aConquistado." : "??fN??vel II: ??cN??o Conquistado."),
                                        (user.isNivel3() ? "??fN??vel III: ??aConquistado." : "??fN??vel III: ??cN??o Conquistado."),
                                        (user.isNivel4() ? "??fN??vel IV: ??aConquistado." : "??fN??vel IV: ??cN??o Conquistado."),
                                        (user.isNivel5() ? "??fN??vel V: ??aConquistado." : "??fN??vel V: ??cN??o Conquistado."),
                                        (user.isNivel6() ? "??fN??vel VI: ??aConquistado." : "??fN??vel VI: ??cN??o Conquistado."),
                                        (user.isNivel7() ? "??fN??vel VII: ??aConquistado." : "??fN??vel VII: ??cN??o Conquistado.")
                                ).setName("??eStatus sua fac????o").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(29, item8);
                                p.updateInventory();
                                mPlayer.getFaction().setMemberBoost(mPlayer.getFaction().getMemberBoost() + 1);

                                AtlasStorage.saveFac(mPlayer.getFactionName(), user, true);
                                return;
                            } else p.sendMessage("??cVoc?? precisa de ??7" + "500.000??c coins.");
                        }
                        return;
                    }
                    if (e.getRawSlot() == 13) {
                        if (user.isNivel4()) {
                            p.sendMessage("??eSua fac????o j?? tem este n??vel de upgrade.");
                        } else {
                            if (!user.isNivel3()) {
                                p.sendMessage("??cSua fac????o precisa do upgrade antigo.");
                                return;
                            }
                            if (coin >= 650000) {
                                SolaryEconomy.economia.substractBalance(p.getName(), BigDecimal.valueOf(650000));
                                p.sendMessage("??eUpgrade realizado com sucesso.");
                                user.setNivel4(true);
                                ItemStack item4 = new ItemBuilder(Material.HAY_BLOCK).setName("??eN??vel IV").setLore("??7Fa??a um upgrade da sua fac????o", "??7para receber v??rias vantagens.", "??7listadas abaixo."
                                        , "??fVantagens:", "", "??e ??? ??7Aumenta +1 vagas na fac????o;",
                                        "??e ??? ??7Adiciona +6% a mais em toda venda de item na loja;",
                                        "??e ??? ??7Adiciona +25% em drops de mobs;",
                                        "??e ??? ??7acrescenta +40% de xp em habilidades.", "", "??fPre??o: ??7650.000",
                                        "", (coin >= 650000 && !user.isNivel4() ? "??cClique para comprar a evolu????o." : (user.isNivel4() ? "??eVoc?? j?? possui essa evolu????o" : "??cVoc?? n??o possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(e.getRawSlot(), item4);
                                ItemStack item8 = new ItemBuilder(Banner.getRedBanner(MPlayer.get(p).getFactionTag())).setLore("??7Status geral dos n??veis:", "",
                                        (user.isNivel1() ? "??fN??vel I: ??aConquistado." : "??fN??vel I: ??cN??o Conquistado."),
                                        (user.isNivel2() ? "??fN??vel II: ??aConquistado." : "??fN??vel II: ??cN??o Conquistado."),
                                        (user.isNivel3() ? "??fN??vel III: ??aConquistado." : "??fN??vel III: ??cN??o Conquistado."),
                                        (user.isNivel4() ? "??fN??vel IV: ??aConquistado." : "??fN??vel IV: ??cN??o Conquistado."),
                                        (user.isNivel5() ? "??fN??vel V: ??aConquistado." : "??fN??vel V: ??cN??o Conquistado."),
                                        (user.isNivel6() ? "??fN??vel VI: ??aConquistado." : "??fN??vel VI: ??cN??o Conquistado."),
                                        (user.isNivel7() ? "??fN??vel VII: ??aConquistado." : "??fN??vel VII: ??cN??o Conquistado.")
                                ).setName("??eStatus sua fac????o").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(29, item8);
                                p.updateInventory();
                                mPlayer.getFaction().setMemberBoost(mPlayer.getFaction().getMemberBoost() + 1);
                                AtlasStorage.saveFac(mPlayer.getFactionName(), user, true);
                                return;
                            } else p.sendMessage("??cVoc?? precisa de ??7" + "650.000??c coins.");
                        }
                        return;
                    }
                    if (e.getRawSlot() == 14) {
                        if (user.isNivel5()) {
                            p.sendMessage("??eSua fac????o j?? tem este n??vel de upgrade.");
                        } else {
                            if (!user.isNivel4()) {
                                p.sendMessage("??cSua fac????o precisa do upgrade antigo.");
                                return;
                            }
                            if (coin >= 800000) {
                                SolaryEconomy.economia.substractBalance(p.getName(), BigDecimal.valueOf(800000));
                                p.sendMessage("??eUpgrade realizado com sucesso.");
                                user.setNivel5(true);
                                ItemStack item5 = new ItemBuilder(Material.DIAMOND).setName("??eN??vel V").setLore("??7Fa??a um upgrade da sua fac????o", "??7para receber v??rias vantagens.", "??7listadas abaixo."
                                        , "??fVantagens:", "", "??e ??? ??7Aumenta +1 vagas na fac????o;",
                                        "??e ??? ??7Acrescenta +1 de poder em todos na fac????o;",
                                        "??e ??? ??7Adiciona +8% a mais em toda venda de item na loja;",
                                        "??e ??? ??7Adiciona +30% em drops de mobs;",
                                        "??e ??? ??7Acrescenta +50% de xp em habilidades.", "", "??fPre??o:??7 800.000",
                                        "", (coin >= 800000 && !user.isNivel5() ? "??cClique para comprar a evolu????o." : (user.isNivel5() ? "??eVoc?? j?? possui essa evolu????o" : "??cVoc?? n??o possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(e.getRawSlot(), item5);
                                ItemStack item8 = new ItemBuilder(Banner.getRedBanner(MPlayer.get(p).getFactionTag())).setLore("??7Status geral dos n??veis:", "",
                                        (user.isNivel1() ? "??fN??vel I: ??aConquistado." : "??fN??vel I: ??cN??o Conquistado."),
                                        (user.isNivel2() ? "??fN??vel II: ??aConquistado." : "??fN??vel II: ??cN??o Conquistado."),
                                        (user.isNivel3() ? "??fN??vel III: ??aConquistado." : "??fN??vel III: ??cN??o Conquistado."),
                                        (user.isNivel4() ? "??fN??vel IV: ??aConquistado." : "??fN??vel IV: ??cN??o Conquistado."),
                                        (user.isNivel5() ? "??fN??vel V: ??aConquistado." : "??fN??vel V: ??cN??o Conquistado."),
                                        (user.isNivel6() ? "??fN??vel VI: ??aConquistado." : "??fN??vel VI: ??cN??o Conquistado."),
                                        (user.isNivel7() ? "??fN??vel VII: ??aConquistado." : "??fN??vel VII: ??cN??o Conquistado.")
                                ).setName("??eStatus sua fac????o").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(29, item8);
                                p.updateInventory();
                                mPlayer.getFaction().setMemberBoost(mPlayer.getFaction().getMemberBoost() + 1);
                                AtlasStorage.saveFac(mPlayer.getFactionName(), user, true);
                                return;
                            } else p.sendMessage("??cVoc?? precisa de ??7" + "800.000??c coins.");
                        }
                        return;
                    }
                    if (e.getRawSlot() == 15) {
                        if (user.isNivel6()) {
                            p.sendMessage("??eSua fac????o j?? tem este n??vel de upgrade.");
                        } else {
                            if (!user.isNivel5()) {
                                p.sendMessage("??cSua fac????o precisa do upgrade antigo.");
                                return;
                            }
                            if (coin >= 1000000) {
                                SolaryEconomy.economia.substractBalance(p.getName(), BigDecimal.valueOf(1000000));
                                p.sendMessage("??eUpgrade realizado com sucesso.");
                                user.setNivel6(true);
                                ItemStack item6 = new ItemBuilder(Material.NETHER_STAR).setName("??eN??vel VI").setLore("??7Fa??a um upgrade da sua fac????o", "??7para receber v??rias vantagens.", "??7listadas abaixo."
                                        , "??fVantagens:", "", "??e ??? ??7Efeitos no claim, Pressa III;",
                                        "??e ??? ??7Acrescenta +2 de poder em todos na fac????o;",
                                        "??e ??? ??7Adiciona +10% a mais em toda venda de item na loja;",
                                        "??e ??? ??7Adiciona +25% em drops de mobs;",
                                        "??e ??? ??7Acrescenta +60% de xp em habilidades;",
                                        "??e ??? ??7Acelera recupera????o de poder em 1 minuto;",
                                        "??e ??? ??7Chance de receber caixas em spawners.", "", "??fPre??o: ??71.000.000", "",
                                        (coin >= 1000000 && !user.isNivel6() ? "??cClique para comprar a evolu????o." : (user.isNivel6() ? "??eVoc?? j?? possui essa evolu????o" : "??cVoc?? n??o possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(e.getRawSlot(), item6);
                                ItemStack item8 = new ItemBuilder(Banner.getRedBanner(MPlayer.get(p).getFactionTag())).setLore("??7Status geral dos n??veis:", "",
                                        (user.isNivel1() ? "??fN??vel I: ??aConquistado." : "??fN??vel I: ??cN??o Conquistado."),
                                        (user.isNivel2() ? "??fN??vel II: ??aConquistado." : "??fN??vel II: ??cN??o Conquistado."),
                                        (user.isNivel3() ? "??fN??vel III: ??aConquistado." : "??fN??vel III: ??cN??o Conquistado."),
                                        (user.isNivel4() ? "??fN??vel IV: ??aConquistado." : "??fN??vel IV: ??cN??o Conquistado."),
                                        (user.isNivel5() ? "??fN??vel V: ??aConquistado." : "??fN??vel V: ??cN??o Conquistado."),
                                        (user.isNivel6() ? "??fN??vel VI: ??aConquistado." : "??fN??vel VI: ??cN??o Conquistado."),
                                        (user.isNivel7() ? "??fN??vel VII: ??aConquistado." : "??fN??vel VII: ??cN??o Conquistado.")
                                ).setName("??eStatus sua fac????o").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(29, item8);
                                p.updateInventory();
                                AtlasStorage.saveFac(mPlayer.getFactionName(), user, true);
                                return;
                            } else p.sendMessage("??cVoc?? precisa de ??7" + "1.000.000??c coins.");
                        }
                        return;
                    }
                    if (e.getRawSlot() == 16) {
                        if (user.isNivel7()) {
                            p.sendMessage("??eSua fac????o j?? tem este n??vel de upgrade.");
                        } else {
                            if (!user.isNivel6()) {
                                p.sendMessage("??cSua fac????o precisa do upgrade antigo.");
                                return;
                            }
                            if (coin >= 1250000) {
                                SolaryEconomy.economia.substractBalance(p.getName(), BigDecimal.valueOf(1250000));
                                p.sendMessage("??eUpgrade realizado com sucesso.");
                                user.setNivel7(true);
                                ItemStack item7 = new ItemBuilder(Material.MOB_SPAWNER).setName("??eN??vel VII").setLore("??7Fa??a um upgrade da sua fac????o", "??7para receber v??rias vantagens.", "??7listadas abaixo."
                                        , "??fVantagens:", "", "??e ??? ??7Aumenta +3 de poder em todos na fac????o;",
                                        "??e ??? ??7Adiciona +0,6 a mais em toda venda de item na loja;",
                                        "??e ??? ??7Adiciona +25% em drops de mobs;",
                                        "??e ??? ??7Acrescenta +40% de xp em habilidades;",
                                        "??e ??? ??7Efeitos no claim, Regenera????o III;",
                                        "??e ??? ??7Agora spawners funcionama mais r??pido;",
                                        "??e ??? ??7Spawners dropam 2x mais;",
                                        "??e ??? ??7Chance de receber itens especiais em spawners.", "", "??fPre??o: ??71.250.000", "",
                                        (coin >= 1250000 && !user.isNivel7() ? "??cClique para comprar a evolu????o." : (user.isNivel7() ? "??eVoc?? j?? possui essa evolu????o" : "??cVoc?? n??o possui dinheiro suficiente."))
                                ).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(e.getRawSlot(), item7);
                                ItemStack item8 = new ItemBuilder(Banner.getRedBanner(MPlayer.get(p).getFactionTag())).setLore("??7Status geral dos n??veis:", "",
                                        (user.isNivel1() ? "??fN??vel I: ??aConquistado." : "??fN??vel I: ??cN??o Conquistado."),
                                        (user.isNivel2() ? "??fN??vel II: ??aConquistado." : "??fN??vel II: ??cN??o Conquistado."),
                                        (user.isNivel3() ? "??fN??vel III: ??aConquistado." : "??fN??vel III: ??cN??o Conquistado."),
                                        (user.isNivel4() ? "??fN??vel IV: ??aConquistado." : "??fN??vel IV: ??cN??o Conquistado."),
                                        (user.isNivel5() ? "??fN??vel V: ??aConquistado." : "??fN??vel V: ??cN??o Conquistado."),
                                        (user.isNivel6() ? "??fN??vel VI: ??aConquistado." : "??fN??vel VI: ??cN??o Conquistado."),
                                        (user.isNivel7() ? "??fN??vel VII: ??aConquistado." : "??fN??vel VII: ??cN??o Conquistado.")
                                ).setName("??eStatus sua fac????o").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                                p.getOpenInventory().setItem(29, item8);
                                p.updateInventory();
                                AtlasStorage.saveFac(mPlayer.getFactionName(), user, true);
                                return;
                            } else p.sendMessage("??cVoc?? precisa de ??7" + "1.250.000??c coins.");

                        }
                    }
        }
    }

}
