/*
 * Copyright (C) 2024 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.settings.tabs.media.torrent.peer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowOutward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.him188.ani.app.ui.foundation.text.ProvideTextStyleContentColor
import me.him188.ani.app.ui.richtext.RichText
import me.him188.ani.app.ui.richtext.rememberBBCodeRichTextState
import me.him188.ani.app.ui.settings.SettingsTab
import me.him188.ani.app.ui.settings.framework.components.SwitchItem
import me.him188.ani.app.ui.settings.framework.components.TextItem

@Composable
private fun BBCodeSupportingText(text: String, modifier: Modifier = Modifier) {
    val richTextState = rememberBBCodeRichTextState(text)
    RichText(richTextState.elements, modifier)
}

@Composable
private fun RuleEditItem(
    content: String,
    enabled: Boolean,
    supportingTextBBCode: String,
    onContentChange: (String) -> Unit,
    textFieldShape: Shape = MaterialTheme.shapes.extraSmall
) {
    val listItemColors = ListItemDefaults.colors(containerColor = Color.Transparent)
    ListItem(
        headlineContent = {
            OutlinedTextField(
                value = content,
                enabled = enabled,
                label = { Text("规则") },
                maxLines = 8,
                onValueChange = onContentChange,
                shape = textFieldShape,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        colors = listItemColors,
        supportingContent = {
            BBCodeSupportingText(supportingTextBBCode, Modifier.padding(8.dp))
        }
    )
}

@Composable
fun PeerFilterEditPane(
    state: PeerFilterSettingsState,
    showIpBlockingItem: Boolean,
    onClickIpBlockSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsTab(modifier.verticalScroll(rememberScrollState())) {
        Group({ Text("过滤规则") }) {
            SwitchItem(
                title = { Text("过滤 IP 地址") },
                checked = state.ipFilterEnabled,
                onCheckedChange = { state.ipFilterEnabled = it },
            )
            AnimatedVisibility(visible = state.ipFilterEnabled) {
                RuleEditItem(
                    content = state.ipFilters,
                    enabled = state.ipFilterEnabled,
                    supportingTextBBCode = """
                        每行一条过滤规则，支持 IPv4 和 IPv6
                        支持以下格式：
                        * 无类别域间路由（CIDR）
                          例如：[code]10.0.0.1/24[/code] 将过滤从 [code]10.0.0.0[/code] 至 [code]10.0.0.255[/code] 的所有 IP
                          [code]ff06:1234::/64[/code] 将过滤从 [code]ff06:1234::[/code] 至 [code]ff06:1234::ffff:ffff:ffff:ffff[/code] 的所有 IP
                        * 通配符
                          例如：[code]10.0.12.*[/code] 将过滤从 [code]10.0.12.0[/code] 至 [code]10.0.12.255[/code] 的所有 IP
                          [code]ff06:1234::*[/code] 将过滤从 [code]ff06:1234::[/code] 至 [code]ff06:1234::ffff[/code] 的所有 IP
                          支持多级通配符，例如 [code]10.0.*.*[/code]
                        * 范围表示
                          例如 [code]10.0.24.100-200[/code] 和 [code]ff06:1234::cafe-dead[/code]
                    """.trimIndent(),
                    onContentChange = { state.ipFilters = it },
                )
            }

            SwitchItem(
                title = { Text("过滤客户端指纹") },
                checked = state.idFilterEnabled,
                onCheckedChange = { state.idFilterEnabled = it },
            )
            AnimatedVisibility(visible = state.idFilterEnabled) {
                Column {
                    RuleEditItem(
                        content = state.idFilters,
                        enabled = state.idFilterEnabled,
                        supportingTextBBCode = """
                        每行一条过滤规则，仅支持使用正则表达式过滤
                        例如：[code]\-HP\d{4}\-[/code] 将封禁具有 -HPxxxx- 指纹的客户端
                    """.trimIndent(),
                        onContentChange = { state.idFilters = it },
                    )
                    SwitchItem(
                        title = { Text("总是过滤异常指纹") },
                        checked = state.blockInvalidId,
                        onCheckedChange = { state.blockInvalidId = it },
                        description = {
                            BBCodeSupportingText("无论是否满足规则, 都会屏蔽指纹不符合 [code]-xxxxxx-[/code] 格式的客户端")
                        },
                    )
                }
            }

            SwitchItem(
                title = { Text("过滤客户端类型") },
                checked = state.clientFilterEnabled,
                onCheckedChange = { state.clientFilterEnabled = it },
            )
            AnimatedVisibility(visible = state.clientFilterEnabled) {
                RuleEditItem(
                    content = state.clientFilters,
                    enabled = state.clientFilterEnabled,
                    supportingTextBBCode = """
                        每行一条过滤规则，仅支持使用正则表达式过滤
                        例如：[code]go\.torrent(\sdev)?[/code] 将封禁百度网盘的离线下载客户端
                    """.trimIndent(),
                    onContentChange = { state.clientFilters = it },
                )
            }

            AnimatedVisibility(visible = showIpBlockingItem) {
                Group(
                    title = { Text("黑名单") },
                    description = { Text("黑名单中的 Peer 总是被屏蔽，无论是否匹配过滤规则") },
                ) {
                    TextItem(
                        title = { Text("IP 黑名单设置") },
                        description = { Text("配置 IP 黑名单列表") },
                        action = {
                            IconButton(onClickIpBlockSettings) {
                                Icon(Icons.Rounded.ArrowOutward, null)
                            }
                        },
                        onClick = onClickIpBlockSettings,
                    )
                }
            }

            TextItem {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                ) {
                    ProvideTextStyleContentColor(
                        MaterialTheme.typography.labelMedium,
                        MaterialTheme.colorScheme.outline,
                    ) {
                        Text("提示：修改自动保存")
                    }
                }
            }
        }
    }
}