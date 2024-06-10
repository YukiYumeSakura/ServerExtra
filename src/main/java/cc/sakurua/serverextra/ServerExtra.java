package cc.sakurua.serverextra;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.GenericStatistic;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ServerExtra extends PlaceholderExpansion{

    @Override
    public @NotNull String getIdentifier() {
        return "serverextra";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Dreamflower";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        return switch (params) {
            case "tps" -> getTpsFormated();
            case "tps_colored" -> getTpsColored();
            case "mspt" -> getMsptFormated();
            case "mspt_colored" -> getMsptColored();
            default -> super.onRequest(player, params);
        };
    }

    public double getTps() {
        double tps = Bukkit.getTPS()[0];
        return Math.min(20.00, Math.round(tps * 100.0) / 100.0);
    }

    public String getTpsFormated(){
        double tps = getTps();
        // 格式化字符串, 显示为2位小数, 缺位补零
        return String.format("%.2f", tps);
    }

    public String getTpsFormated(double tps){
        // 格式化字符串, 显示为2位小数, 缺位补零
        return String.format("%.2f", tps);
    }

    public String getTpsColored() {
        double tps = getTps();
        String color;
        if (tps >= 19) {
            color = "§a";
        } else if (tps >= 15) {
            color = "§e";
        } else {
            color = "§c";
        }
        return color + getTpsFormated(tps);
    }

    public double getMspt() {
        Spark spark = SparkProvider.get();
        GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> mspt = spark.mspt();
        double mspt_current = 0;
        if (mspt != null) {
            // 平均值
            mspt_current = Math.round((mspt.poll()[0].mean()) * 100.0) / 100.0;
        }
        return mspt_current;
    }

    public String getMsptFormated(){
        double mspt = getMspt();
        // 格式化字符串, 显示为2位小数, 缺位补零
        return String.format("%.2f", mspt);
    }

    public String getMsptFormated(double mspt){
        // 格式化字符串, 显示为2位小数, 缺位补零
        return String.format("%.2f", mspt);
    }

    public String getMsptColored() {
        double mspt = getMspt();
        String color;
        if (mspt <= 40) {
            color = "§a";
        } else if (mspt <= 50) {
            color = "§e";
        } else {
            color = "§c";
        }
        return color + getMsptFormated(mspt);
    }
}
